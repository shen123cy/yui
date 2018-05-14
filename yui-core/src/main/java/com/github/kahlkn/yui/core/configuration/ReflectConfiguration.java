package com.github.kahlkn.yui.core.configuration;

import com.github.kahlkn.artoria.aop.Enhancer;
import com.github.kahlkn.artoria.aop.Interceptor;
import com.github.kahlkn.artoria.exception.ExceptionUtils;
import com.github.kahlkn.artoria.reflect.ReflectUtils;
import com.github.kahlkn.artoria.reflect.Reflecter;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.yui.core.util.DataLoader;
import com.github.kahlkn.yui.core.util.SimpleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Reflect auto configuration.
 * @author Kahle
 */
@Configuration
public class ReflectConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(ReflectConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        Reflecter reflecter = ReflectUtils.getReflecter();
        if (reflecter != null) {
            Reflecter instance = CacheEnhancer.getInstance(reflecter);
            ReflectUtils.setReflecter(instance);
        }
    }

    @Override
    public void destroy() throws Exception {
    }

    private static class CacheEnhancer {
        private static SimpleCache simpleCache = new SimpleCache();

        public static Reflecter getInstance(Reflecter reflecter) {
            Assert.notNull(reflecter, "Parameter \"reflecter\" must not null. ");
            ReflecterInterceptor intr = new ReflecterInterceptor(reflecter);
            return (Reflecter) Enhancer.enhance(reflecter, intr);
        }

        private static class DataLoaderImpl implements DataLoader {
            private Object object;
            private Method method;
            private Object[] args;

            public DataLoaderImpl(Object object, Method method, Object[] args) {
                this.object = object;
                this.method = method;
                this.args = args;
            }

            @Override
            public Object load() {
                try {
                    return method.invoke(object, args);
                }
                catch (Exception e) {
                    throw ExceptionUtils.wrap(e);
                }
            }

        }

        private static class ReflecterInterceptor implements Interceptor {
            private static final List<String> METHOD_NAMES;
            private Reflecter original;

            static {
                List<String> list = new ArrayList<String>();
                Collections.addAll(list, "forName"
                        , "findConstructors", "findConstructor"
                        , "findFields", "findDeclaredFields"
                        , "findAccessFields", "findField"
                        , "findMethods", "findDeclaredMethods"
                        , "findAccessMethods", "findMethod"
                        , "findSimilarMethod");
                METHOD_NAMES = Collections.unmodifiableList(list);
            }

            ReflecterInterceptor(Reflecter original) {
                this.original = original;
            }

            @Override
            public Object intercept(Object proxyObject, Method method, Object[] args) throws Throwable {
                if (METHOD_NAMES.contains(method.getName())) {
                    DataLoader loader = new DataLoaderImpl(original, method, args);
                    String key = method.getName() + Arrays.toString(args);
                    return simpleCache.get(key, loader);
                }
                else {
                    return method.invoke(original, args);
                }
            }

        }

    }

}
