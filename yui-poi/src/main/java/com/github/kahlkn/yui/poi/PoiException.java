package com.github.kahlkn.yui.poi;

import com.github.kahlkn.artoria.exception.UncheckedException;

/**
 * POI exception.
 * @author Kahle
 */
public class PoiException extends UncheckedException {

    public PoiException() {
        super();
    }

    public PoiException(String message) {
        super(message);
    }

    public PoiException(Throwable cause) {
        super(cause);
    }

    public PoiException(String message, Throwable cause) {
        super(message, cause);
    }

}
