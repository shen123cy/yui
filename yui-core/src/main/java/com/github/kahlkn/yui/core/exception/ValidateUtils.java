package com.github.kahlkn.yui.core.exception;

import com.github.kahlkn.artoria.util.ArrayUtils;
import com.github.kahlkn.artoria.util.CollectionUtils;
import com.github.kahlkn.artoria.util.MapUtils;
import com.github.kahlkn.artoria.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Validate tools.
 * @author Kahle
 */
public class ValidateUtils {

    public static void state(boolean expression, ErrorCode code) {
        if (!expression) {
            throw new BusinessException(code);
        }
    }

    public static void isFalse(boolean expression, ErrorCode code) {
        if (expression) {
            throw new BusinessException(code);
        }
    }

    public static void isTrue(boolean expression, ErrorCode code) {
        if (!expression) {
            throw new BusinessException(code);
        }
    }

    public static void isNull(Object object, ErrorCode code) {
        if (object != null) {
            throw new BusinessException(code);
        }
    }

    public static void notNull(Object object, ErrorCode code) {
        if (object == null) {
            throw new BusinessException(code);
        }
    }

    public static void isEmpty(byte[] array, ErrorCode code) {
        if (ArrayUtils.isNotEmpty(array)) {
            throw new BusinessException(code);
        }
    }

    public static void notEmpty(byte[] array, ErrorCode code) {
        if (ArrayUtils.isEmpty(array)) {
            throw new BusinessException(code);
        }
    }

    public static void isEmpty(Object[] array, ErrorCode code) {
        if (ArrayUtils.isNotEmpty(array)) {
            throw new BusinessException(code);
        }
    }

    public static void notEmpty(Object[] array, ErrorCode code) {
        if (ArrayUtils.isEmpty(array)) {
            throw new BusinessException(code);
        }
    }

    public static void isEmpty(Collection<?> collection, ErrorCode code) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new BusinessException(code);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode code) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(code);
        }
    }

    public static void isEmpty(Map<?, ?> map, ErrorCode code) {
        if (MapUtils.isNotEmpty(map)) {
            throw new BusinessException(code);
        }
    }

    public static void notEmpty(Map<?, ?> map, ErrorCode code) {
        if (MapUtils.isEmpty(map)) {
            throw new BusinessException(code);
        }
    }

    public static void isEmpty(String text, ErrorCode code) {
        if (StringUtils.isNotEmpty(text)) {
            throw new BusinessException(code);
        }
    }

    public static void notEmpty(String text, ErrorCode code) {
        if (StringUtils.isEmpty(text)) {
            throw new BusinessException(code);
        }
    }

    public static void isBlank(String text, ErrorCode code) {
        if (StringUtils.isNotBlank(text)) {
            throw new BusinessException(code);
        }
    }

    public static void notBlank(String text, ErrorCode code) {
        if (StringUtils.isBlank(text)) {
            throw new BusinessException(code);
        }
    }

}
