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
public class CryptoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(CryptoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        this.loadBouncyCastle();
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            log.debug("Provider: " + provider.getClass().getName()
                    + "(Version: " + provider.getVersion() + ")");
        }
    }

    @Override
    public void destroy() throws Exception {
    }

    private void loadBouncyCastle() {
        String className = "org.bouncycastle.jce.provider.BouncyCastleProvider";
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        if (!ClassUtils.isPresent(className, loader)) {
            return;
        }
        try {
            Object o = ReflectUtils.newInstance(className);
            Provider provider = (Provider) o;
            Security.addProvider(provider);
            log.info("Init " + provider.getClass().getName()
                    + " " + provider.getVersion());
        }
        catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
    }

}
