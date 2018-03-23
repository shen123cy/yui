package com.github.kahlkn.yui.test;

/**
 * Test task.
 * @author Kahle
 */
public interface TestTask {

    /**
     * Execute tested code.
     */
    void execute();

    /**
     * Get test status.
     * @return Test status
     */
    boolean getStatus();

    /**
     * Get test name.
     * @return Test name
     */
    String getName();

    /**
     * Get test description.
     * @return Test description
     */
    String getDescription();

    /**
     * Get test result.
     * @return Test result
     */
    String getResult();

}
