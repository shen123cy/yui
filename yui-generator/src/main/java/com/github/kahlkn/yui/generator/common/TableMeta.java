package com.github.kahlkn.yui.generator.common;

import java.io.Serializable;
import java.util.List;

/**
 * Database table necessary information.
 * @author Kahle
 */
public class TableMeta implements Serializable {

    /**
     * "TABLE_NAME"
     */
    private String name;

    /**
     * "REMARKS"
     */
    private String remarks;

    /**
     * Table primary keys.
     * If is Composite Primary Key, use "," separate.
     */
    private String primaryKey;

    /**
     * Table all columns.
     */
    private List<ColumnMeta> columns;

    /**
     * Table name you want.
     */
    private String targetName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<ColumnMeta> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMeta> columns) {
        this.columns = columns;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

}
