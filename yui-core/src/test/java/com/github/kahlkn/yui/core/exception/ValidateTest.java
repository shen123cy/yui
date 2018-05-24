package com.github.kahlkn.yui.core.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ValidateTest {
    private Logger log = LoggerFactory.getLogger(ValidateTest.class);

    @Test
    public void test1() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            Validate.notEmpty(data
                    , SystemCode.code1
                    , "Data should not null in here. "
            );
        }
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void test2() {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            Validate.notEmpty(data
                    , "Please input something first."
                    , "Data should not null in here. "
            );
        }
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }
    }

}
