package com.github.kahlkn.yui.core.logging;

import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import org.junit.Before;
import org.junit.Test;

public class Log4jTest {

    @Before
    public void init() {
        LoggerFactory.setLoggerAdapter(new Log4jLoggerAdapter());
    }

    @Test
    public void test1() {
        Logger log = LoggerFactory.getLogger(Log4jTest.class);
        log.info("Hello, World! ");
        log.error("Hello, World! ");
    }

}
