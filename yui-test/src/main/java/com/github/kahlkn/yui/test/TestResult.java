package com.github.kahlkn.yui.test;

/**
 * Test result.
 * @author Kahle
 */
public class TestResult {
    private boolean status = false;
    private String name;
    private String description;
    private String report;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

}
