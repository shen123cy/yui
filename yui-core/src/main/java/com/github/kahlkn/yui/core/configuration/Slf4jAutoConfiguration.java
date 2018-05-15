package com.github.kahlkn.yui.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Slf4j auto configuration.
 * @author Kahle
 */
@Configuration
public class Slf4jAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(Slf4jAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        // Optionally remove existing handlers attached to j.u.l root logger
        // (since SLF4J 1.6.5)
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
    }

    @Override
    public void destroy() throws Exception {
    }

}
