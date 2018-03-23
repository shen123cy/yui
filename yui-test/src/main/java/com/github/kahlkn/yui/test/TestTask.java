package com.github.kahlkn.yui.test;

/**
 * Test task.
 * @author Kahle
 */
public interface TestTask {

    /**
     * Get test task name.
     * @return Test task name
     */
    String getName();

    /**
     * Set test task name.
     * @param name Test task name
     */
    void setName(String name);

    /**
     * Get test task description.
     * @return Test task description
     */
    String getDescription();

    /**
     * Set test task description.
     * @param description Test task description
     */
    void setDescription(String description);

    /**
     * Get be tested code.
     * @return Be tested code object
     */
    TestCode getTestCode();

    /**
     * Set be tested code.
     * @param testCode Be tested code object
     */
    void setTestCode(TestCode testCode);

    /**
     * Get test result.
     * @return Test result object
     */
    TestResult getResult();

    /**
     * Execute test task.
     */
    void execute();

}
