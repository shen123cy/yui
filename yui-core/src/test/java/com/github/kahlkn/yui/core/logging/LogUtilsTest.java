package com.github.kahlkn.yui.core.logging;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LogUtilsTest {

    @Test
    public void test1() {
        Logger log = LogUtils.log4j(LogUtilsTest.class);
        log.error("Hello, World! ");
    }

}
