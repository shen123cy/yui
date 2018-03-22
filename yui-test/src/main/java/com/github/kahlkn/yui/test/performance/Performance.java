package com.github.kahlkn.sagiri.test.performance;

import com.github.kahlkn.sagiri.test.Tested;

import java.util.ArrayList;
import java.util.List;

/**
 * Single thread performance.
 * @author Kahle
 */
public class Performance extends BasePerformance {

    public Performance(Integer groupTotal, Integer eachGroupTotal, Integer dataCalcTotal, Tested tested) {
        super(groupTotal, eachGroupTotal, dataCalcTotal, tested);
    }

    @Override
    public void execute() {
        rawData = new ArrayList<List<Long>>();
        try {
            for (int g = 0; g < groupTotal; g++) {
                List<Long> list = new ArrayList<Long>();
                long start, end;
                for (int i = 0; i < eachGroupTotal; i++) {
                    start = System.currentTimeMillis();
                    for (int j = 0; j < dataCalcTotal; j++) {
                        tested.call();
                    }
                    end = System.currentTimeMillis();
                    list.add(end - start);
                }
                rawData.add(list);
            }
            status = true;
        }
        catch (Throwable t) {
            status = false;
            throwable = t;
        }
    }

}
