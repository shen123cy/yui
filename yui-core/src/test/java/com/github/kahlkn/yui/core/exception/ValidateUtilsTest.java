package com.github.kahlkn.yui.core.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ValidateUtilsTest {
    private Logger log = LoggerFactory.getLogger(ValidateUtilsTest.class);

    @Test
    public void test1() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            ValidateUtils.notEmpty(data, SystemCode.code1);
        }
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void test2() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            ValidateUtils.notEmpty(data, SystemCode.code2);
        }
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }
    }

}
