package com.github.kahlkn.yui.test.support;

import com.github.kahlkn.yui.test.TestCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Multi thread performance.
 * @author Kahle
 */
public class MultiPerformance extends BasePerformance {

    @Override
    protected void execute(List<List<Long>> rawData, Integer groupTotal
            , Integer eachGroupTotal, final Integer dataCalcTotal, final TestCode testCode) throws Exception {
        for (int g = 0; g < groupTotal; g++) {
            List<Future<Long>> list = new ArrayList<Future<Long>>();
            // Create thread pool.
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            ExecutorService pool = new ThreadPoolExecutor(eachGroupTotal, eachGroupTotal, 0L
                    , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
            // Do operation.
            for (int i = 0; i < eachGroupTotal; i++) {
                Callable<Long> callable = new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        long start = System.currentTimeMillis();
                        for (int j = 0; j < dataCalcTotal; j++) {
                            testCode.call();
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
    }

}
