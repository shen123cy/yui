package com.github.kahlkn.yui.core.exception;

import com.github.kahlkn.artoria.exception.UncheckedException;

/**
 * Business exception.
 * @author Kahle
 */
public class BusinessException extends UncheckedException {
    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getContent());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getContent(), cause);
        this.errorCode = errorCode;
    }

}
