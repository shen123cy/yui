package com.github.kahlkn.yui.core.exception;

import com.github.kahlkn.artoria.exception.UncheckedException;
import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import org.junit.Test;

public class BusinessExceptionTest {
    private static Logger log = LoggerFactory.getLogger(BusinessExceptionTest.class);

    @Test
    public void createAndSetOthers() {
        try {
            throw new BusinessException(SystemCode.code1)
                    .setDescription("address in %s, say \"%s\". ", "0364294", "hello");
        }
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void throwException1() throws BusinessException {
        try {
            throw new BusinessException(SystemCode.code1)
                    .setDescription("[BusinessException]: Just Test...");
        }
        catch (BusinessException e) {
            throw BusinessException.wrap(e);
        }
    }

    private void throwException2() throws BusinessException {
        try {
            throw new UncheckedException(
                    "[UncheckedException]: Just Test...");
        }
        catch (Exception e) {
            throw BusinessException.wrap(e);
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
