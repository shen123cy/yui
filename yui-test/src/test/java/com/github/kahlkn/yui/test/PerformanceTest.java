package com.github.kahlkn.sagiri.test;

import com.github.kahlkn.sagiri.test.performance.MultiPerformance;
import com.github.kahlkn.sagiri.test.performance.Performance;
import org.junit.Test;

import java.util.Calendar;

public class PerformanceTest {
    private Integer groupTotal = 20;
    private Integer eachGroupTotal = 20;
    private Integer dataCalcTotal = 20000;

    @Test
    public void test1() {
        Tested newObj = new Tested() {
            @Override
            public void call() throws Exception {
                new Object();
                throw new RuntimeException();
            }
        };
        Tested newCal = new Tested() {
            @Override
            public void call() throws Exception {
                Calendar.getInstance();
            }
        };

        Performance newObjPer = new Performance(groupTotal, eachGroupTotal, dataCalcTotal, newObj);
        newObjPer.setName("New Object single thread");
        MultiPerformance newObjMulti = new MultiPerformance(groupTotal, eachGroupTotal, dataCalcTotal, newObj);
        newObjMulti.setName("New Object multi thread");
        Performance newCalPer = new Performance(groupTotal, eachGroupTotal, dataCalcTotal, newCal);
        newCalPer.setName("Calendar get instance single thread");
        MultiPerformance newCalMulti = new MultiPerformance(groupTotal, eachGroupTotal, dataCalcTotal, newCal);
        newCalMulti.setName("Calendar get instance multi thread");

        Tester tester = new Tester()
                .setTitle("Performance test")
                .setDescription("Let's do performance test. ")
                .addTasks(newObjPer, newObjMulti, newCalPer, newCalMulti)
                .execute();
        System.out.println(tester);
    }

}
