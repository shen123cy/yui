package com.github.kahlkn.yui.core.exception;

import com.github.kahlkn.artoria.exception.ExceptionUtils;
import com.github.kahlkn.artoria.exception.UncheckedException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessExceptionTest {
    private static Logger log = LoggerFactory.getLogger(BusinessExceptionTest.class);

    @Test
    public void createAndSetOthers() {
        try {
            throw new BusinessException(SystemCode.code1);
        }
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void throwException1() throws BusinessException {
        try {
            throw new BusinessException(SystemCode.code1);
        }
        catch (BusinessException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    private void throwException2() throws BusinessException {
        try {
            throw new UncheckedException("[UncheckedException]: Just Test...");
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    @Test
    public void testWrap1() {
        try {
            this.throwException1();
        }
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void testWrap2() {
        try {
            this.throwException2();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
