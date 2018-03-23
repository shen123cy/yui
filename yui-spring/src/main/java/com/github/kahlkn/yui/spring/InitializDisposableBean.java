package com.github.kahlkn.yui.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Add subclass in applicationContext.xml
 * <pre>
 *     <bean class="subclass reference" />
 * </pre>
 * approach to
 * <pre>
 *     <bean class="reference" init-method="init" destroy-method="destroy" />
 * </pre>
 */
public abstract class InitializDisposableBean implements InitializingBean, DisposableBean {
}
