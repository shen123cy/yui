package com.github.kahlkn.yui.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Add subclass in applicationContext.xml
 * <pre>
 *     <bean class="subclass reference" />
 * </pre>
 */
public abstract class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
}
