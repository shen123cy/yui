package com.github.kahlkn.yui.core.exception;

/**
 * Error code.
 * @author Kahle
 */
public interface ErrorCode {

    /**
     * The code
     * @return The code string
     */
    String getCode();

    /**
     * The code description
     * @return The code description
     */
    String getContent();

}
