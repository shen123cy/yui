package com.github.kahlkn.yui.test;

import com.github.kahlkn.yui.test.support.MultiPerformance;
import com.github.kahlkn.yui.test.support.SinglePerformance;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PerformanceTest {

    @Test
    public void test1() {
        TestCode newObj = new TestCode() {
            @Override
            public void call() throws Exception {
                new Object();
                throw new RuntimeException();
            }
        };
        TestCode newCal = new TestCode() {
            @Override
            public void call() throws Exception {
                Calendar.getInstance();
            }
        };

        List<TestTask> tasks = new ArrayList<TestTask>();

        TestUtils.addTask(tasks, SinglePerformance.class, "New Object single thread", newObj);
        TestUtils.addTask(tasks, MultiPerformance.class, "New Object multi thread", newObj);
        TestUtils.addTask(tasks, SinglePerformance.class, "Calendar get instance single thread", newCal);
        TestUtils.addTask(tasks, MultiPerformance.class, "Calendar get instance multi thread", newCal);

        List<TestResult> results = TestUtils.execute(tasks);
        System.out.println(TestUtils.toString(results));
    }

}
