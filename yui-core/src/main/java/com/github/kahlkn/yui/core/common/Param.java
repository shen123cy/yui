package com.github.kahlkn.yui.core.common;

import java.io.Serializable;

/**
 * Uniform parameter input object.
 * @param <T> Data type
 * @author Kahle
 */
public class Param<T> implements Serializable {
    private Object operatorId;
    private String operatorName;
    private T data;

    public Object getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Object operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
