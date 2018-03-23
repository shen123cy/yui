package com.github.kahlkn.yui.test.performance;

import com.github.kahlkn.yui.test.Tested;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Multi thread performance.
 * @author Kahle
 */
public class MultiPerformance extends BasePerformance {

    public MultiPerformance(Integer groupTotal, Integer eachGroupTotal, Integer dataCalcTotal, Tested tested) {
        super(groupTotal, eachGroupTotal, dataCalcTotal, tested);
    }

    @Override
    public void execute() {
        rawData = new ArrayList<List<Long>>();
        try {
            for (int g = 0; g < groupTotal; g++) {
                List<Future<Long>> list = new ArrayList<Future<Long>>();
                // Create thread pool.
                ExecutorService pool = Executors.newFixedThreadPool(eachGroupTotal);
                // Do operation.
                for (int i = 0; i < eachGroupTotal; i++) {
                    Callable<Long> callable = new Callable<Long>() {
                        @Override
                        public Long call() throws Exception {
                            long start = System.currentTimeMillis();
                            for (int j = 0; j < dataCalcTotal; j++) {
                                tested.call();
                            }
                            long end = System.currentTimeMillis();
                            return end - start;
                        }
                    };
                    Future<Long> f = pool.submit(callable);
                    list.add(f);
                }
                pool.shutdown();
                // Handle result.
                List<Long> dataList = new ArrayList<Long>();
                for (Future<Long> f : list) {
                    dataList.add(f.get());
                }
                rawData.add(dataList);
            }
            status = true;
        }
        catch (Throwable t) {
            status = false;
            throwable = t;
        }
    }

}
