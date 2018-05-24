package com.github.kahlkn.yui.core.util;

/**
 * A function, you can convert function to object.
 * @author Kahle
 */
public interface Function {

    /**
     * Call function.
     * @param args Parameters you want
     * @return Return you want
     */
    Object call(Object... args);

}
