package com.github.kahlkn.yui.test.support;

import com.github.kahlkn.yui.test.TestCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Single thread performance.
 * @author Kahle
 */
public class SinglePerformance extends BasePerformance {

    @Override
    protected void execute(List<List<Long>> rawData, Integer groupTotal
            , Integer eachGroupTotal, final Integer dataCalcTotal, final TestCode testCode) throws Exception {
        for (int g = 0; g < groupTotal; g++) {
            List<Long> list = new ArrayList<Long>();
            long start, end;
            for (int i = 0; i < eachGroupTotal; i++) {
                start = System.currentTimeMillis();
                for (int j = 0; j < dataCalcTotal; j++) {
                    testCode.call();
                }
                end = System.currentTimeMillis();
                list.add(end - start);
            }
            rawData.add(list);
        }
    }

}
