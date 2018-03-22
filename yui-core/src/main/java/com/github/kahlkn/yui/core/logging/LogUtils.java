package com.github.kahlkn.yui.core.logging;

import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;

/**
 * Log tools.
 * @author Kahle
 */
public class LogUtils {

    public static org.slf4j.Logger slf4j(Class<?> clazz) {
        return org.slf4j.LoggerFactory.getLogger(clazz);
    }

    public static org.slf4j.Logger slf4j(String name) {
        return org.slf4j.LoggerFactory.getLogger(name);
    }

    public static org.apache.commons.logging.Log jcl(Class<?> clazz) {
        return org.apache.commons.logging.LogFactory.getLog(clazz);
    }

    public static org.apache.commons.logging.Log jcl(String name) {
        return org.apache.commons.logging.LogFactory.getLog(name);
    }

    public static java.util.logging.Logger jul(String name) {
        return java.util.logging.Logger.getLogger(name);
    }

    public static java.util.logging.Logger jul(Class<?> clazz) {
        return java.util.logging.Logger.getLogger(clazz.getName());
    }

    public static org.apache.log4j.Logger log4j(Class<?> clazz) {
        return org.apache.log4j.Logger.getLogger(clazz);
    }

    public static org.apache.log4j.Logger log4j(String name) {
        return org.apache.log4j.Logger.getLogger(name);
    }

    public static Logger artoria(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger artoria(String name) {
        return LoggerFactory.getLogger(name);
    }

}
