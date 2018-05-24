package com.github.kahlkn.yui.core.configuration;

import com.github.kahlkn.artoria.aop.Enhancer;
import com.github.kahlkn.artoria.aop.ProxyFactory;
import com.github.kahlkn.artoria.beans.BeanCopier;
import com.github.kahlkn.artoria.beans.BeanMap;
import com.github.kahlkn.artoria.beans.BeanUtils;
import com.github.kahlkn.artoria.util.ClassUtils;
import com.github.kahlkn.yui.core.aop.CglibProxyFactory;
import com.github.kahlkn.yui.core.aop.SpringCglibProxyFactory;
import com.github.kahlkn.yui.core.beans.CglibBeanCopier;
import com.github.kahlkn.yui.core.beans.CglibBeanMap;
import com.github.kahlkn.yui.core.beans.SpringCglibBeanCopier;
import com.github.kahlkn.yui.core.beans.SpringCglibBeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * BeanUtils auto configuration.
 * @author Kahle
 */
@Configuration
public class BeanUtilsAutoConfiguration implements InitializingBean, DisposableBean {
    private static final String SPRING_CGLIB_CLASS = "org.springframework.cglib.proxy.MethodInterceptor";
    private static final String CGLIB_CLASS = "net.sf.cglib.proxy.MethodInterceptor";
    private static Logger log = LoggerFactory.getLogger(BeanUtilsAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        Class<? extends BeanMap> beanMapClass = null;
        ProxyFactory proxyFactory = null;
        BeanCopier beanCopier = null;
        if (ClassUtils.isPresent(SPRING_CGLIB_CLASS, classLoader)) {
            beanMapClass = SpringCglibBeanMap.class;
            proxyFactory = new SpringCglibProxyFactory();
            beanCopier = new SpringCglibBeanCopier();
        }
        else if (ClassUtils.isPresent(CGLIB_CLASS, classLoader)) {
            beanMapClass = CglibBeanMap.class;
            proxyFactory = new CglibProxyFactory();
            beanCopier = new CglibBeanCopier();
        }
        else {
            // Do what?
        }
        if (beanMapClass != null) { BeanUtils.setBeanMapClass(beanMapClass); }
        if (proxyFactory != null) { Enhancer.setProxyFactory(proxyFactory); }
        if (beanCopier != null) { BeanUtils.setBeanCopier(beanCopier); }
    }

    @Override
    public void destroy() throws Exception {
    }

}
