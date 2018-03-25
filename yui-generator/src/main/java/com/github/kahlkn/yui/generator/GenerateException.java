package com.github.kahlkn.yui.generator;

import com.github.kahlkn.artoria.exception.UncheckedException;

/**
 * Generate exception.
 * @author Kahle
 */
public class GenerateException extends UncheckedException {

    public GenerateException() {
        super();
    }

    public GenerateException(String message) {
        super(message);
    }

    public GenerateException(Throwable cause) {
        super(cause);
    }

    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

}
