package com.github.kahlkn.yui.core.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring project "ApplicationContext" tools.
 *
 * If in traditional spring project, you must add below code in "applicationContext.xml".
 * <pre>
 *      <bean class="com.github.kahlkn.yui.core.spring.ApplicationContextUtils" lazy-init="false" />
 * </pre>
 *
 * @author Kahle
 */
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtils.setContext(applicationContext);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        ApplicationContextUtils.context = context;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static Object getBean(String name, Object... args) {
        return context.getBean(name, args);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return context.getBean(requiredType, args);
    }

}
