package com.github.kahlkn.yui.core.configuration;

import com.github.kahlkn.artoria.reflect.ReflectUtils;
import com.github.kahlkn.artoria.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.security.Provider;
import java.security.Security;

/**
 * Crypto auto configuration.
 * @author Kahle
 */
@Configuration
public class CryptoAutoConfiguration implements InitializingBean, DisposableBean {
    private static final String BOUNCY_CASTLE_CLASS = "org.bouncycastle.jce.provider.BouncyCastleProvider";
    private static Logger log = LoggerFactory.getLogger(CryptoAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        this.loadBouncyCastle();
    }

    @Override
    public void destroy() throws Exception {
    }

    private void loadBouncyCastle() {
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        if (!ClassUtils.isPresent(BOUNCY_CASTLE_CLASS, loader)) {
            return;
        }
        try {
            Object o = ReflectUtils.newInstance(BOUNCY_CASTLE_CLASS);
            Provider provider = (Provider) o;
            Security.addProvider(provider);
            String name = provider.getClass().getName();
            double version = provider.getVersion();
            log.info("Initialize {} {} success. ", name, version);
        }
        catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

}
