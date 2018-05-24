package com.github.kahlkn.yui.core.configuration;

import com.github.kahlkn.yui.core.spring.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Spring auto configuration.
 * @author Kahle
 */
@Configuration
public class SpringAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(SpringAutoConfiguration.class);
    private ApplicationContext applicationContext;

    @Autowired
    public SpringAutoConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (applicationContext != null) {
            ApplicationContextUtils.setContext(applicationContext);
            log.info("Initialize \"ApplicationContextUtils\" success. ");
        }
    }

    @Override
    public void destroy() throws Exception {
    }

}
