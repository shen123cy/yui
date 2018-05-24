package com.github.kahlkn.yui.core.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Initialize and destroy bean from "InitializingBean" and "DisposableBean".
 * @author Kahle
 */
public interface InitializeDisposableBean extends InitializingBean, DisposableBean {
}
