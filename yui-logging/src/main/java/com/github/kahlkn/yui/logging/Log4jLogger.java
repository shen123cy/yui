package com.github.kahlkn.yui.logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Log4j logger.
 * @author Kahle
 */
public class Log4jLogger implements com.github.kahlkn.artoria.logging.Logger {

    private Logger logger;

    public Log4jLogger(Logger logger) {
        this.logger = logger;
    }

    private void log(Level level, String message, Throwable t) {
        StackTraceElement element = new Throwable().getStackTrace()[2];
        String clazzName = element.getClassName();
        logger.log(clazzName, level, message, t);
    }

    @Override
    public void trace(String msg) {
        if (!this.isTraceEnabled()) {
            return;
        }
        this.log(Level.TRACE, msg, null);
    }

    @Override
    public void trace(Throwable e) {
        if (!this.isTraceEnabled()) {
            return;
        }
        this.log(Level.TRACE, e.getMessage(), e);
    }

    @Override
    public void trace(String msg, Throwable e) {
        if (!this.isTraceEnabled()) {
            return;
        }
        this.log(Level.TRACE, msg, e);
    }

    @Override
    public void debug(String msg) {
        if (!this.isDebugEnabled()) {
            return;
        }
        this.log(Level.DEBUG, msg, null);
    }

    @Override
    public void debug(Throwable e) {
        if (!this.isDebugEnabled()) {
            return;
        }
        this.log(Level.DEBUG, e.getMessage(), e);
    }

    @Override
    public void debug(String msg, Throwable e) {
        if (!this.isDebugEnabled()) {
            return;
        }
        this.log(Level.DEBUG, msg, e);
    }

    @Override
    public void info(String msg) {
        if (!this.isInfoEnabled()) {
            return;
        }
        this.log(Level.INFO, msg, null);
    }

    @Override
    public void info(Throwable e) {
        if (!this.isInfoEnabled()) {
            return;
        }
        this.log(Level.INFO, e.getMessage(), e);
    }

    @Override
    public void info(String msg, Throwable e) {
        if (!this.isInfoEnabled()) {
            return;
        }
        this.log(Level.INFO, msg, e);
    }

    @Override
    public void warn(String msg) {
        if (!this.isWarnEnabled()) {
            return;
        }
        this.log(Level.WARN, msg, null);
    }

    @Override
    public void warn(Throwable e) {
        if (!this.isWarnEnabled()) {
            return;
        }
        this.log(Level.WARN, e.getMessage(), e);
    }

    @Override
    public void warn(String msg, Throwable e) {
        if (!this.isWarnEnabled()) {
            return;
        }
        this.log(Level.WARN, msg, e);
    }

    @Override
    public void error(String msg) {
        if (!this.isErrorEnabled()) {
            return;
        }
        this.log(Level.ERROR, msg, null);
    }

    @Override
    public void error(Throwable e) {
        if (!this.isErrorEnabled()) {
            return;
        }
        this.log(Level.ERROR, e.getMessage(), e);
    }

    @Override
    public void error(String msg, Throwable e) {
        if (!this.isErrorEnabled()) {
            return;
        }
        this.log(Level.ERROR, msg, e);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isEnabledFor(Level.TRACE);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isEnabledFor(Level.DEBUG);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isEnabledFor(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isEnabledFor(Level.WARN);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isEnabledFor(Level.ERROR);
    }

}
