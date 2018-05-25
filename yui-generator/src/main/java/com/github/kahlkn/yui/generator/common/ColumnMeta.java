package com.github.kahlkn.yui.generator.common;

import java.io.Serializable;

/**
 * Database table's column necessary information.
 * @author Kahle
 */
public class ColumnMeta implements Serializable {

    /**
     * "COLUMN_NAME"
     */
    private String name;

    /**
     * "TYPE_NAME"
     */
    private String type;

    /**
     * "COLUMN_SIZE" maybe is "null"
     */
    private Integer size;

    /**
     * "DECIMAL_DIGITS"
     */
    private Integer decimalDigits;

    /**
     * "REMARKS"
     */
    private String remarks;

    /**
     * "COLUMN_DEF"
     */
    private String defaultValue;

    /**
     * "IS_NULLABLE" is "NO" or "YES"
     */
    private String isNullable;

    /**
     * "IS_AUTOINCREMENT" is "NO" or "YES"
     */
    private String isAutoincrement;

    /**
     * Is primary key.
     */
    private Boolean isPrimaryKey;

    /**
     * Column name you want.
     */
    private String targetName;

    /**
     * Target type you want.
     */
    private String targetType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String nullable) {
        isNullable = nullable;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public void setIsAutoincrement(String autoincrement) {
        isAutoincrement = autoincrement;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

}
