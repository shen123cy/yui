package com.github.kahlkn.yui.zookeeper;

import com.github.kahlkn.artoria.lock.LockUtils;
import com.github.kahlkn.artoria.util.ThreadUtils;
import com.github.kahlkn.yui.zookeeper.lock.ZkReentrantLock;
import com.github.kahlkn.yui.zookeeper.lock.ZkReentrantLockFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.*;

@Ignore
public class ZkReentrantLockTest {
    private ExecutorService pool;
    private Integer num = 100;

    @Before
    public void init() {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        pool = new ThreadPoolExecutor(10, 10, 0L
                , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
        LockUtils.registerFactory(ZkReentrantLock.class, new ZkReentrantLockFactory("127.0.0.1:2181"));
        LockUtils.registerLock(ZkReentrantLockTest.class.getName(), ZkReentrantLock.class);
    }

    @After
    public void destroy() {
        pool.shutdown();
        LockUtils.unregisterLock(ZkReentrantLockTest.class.getName());
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 1000000; j++) {
                            if (num >= 0) {
                                LockUtils.lock(ZkReentrantLockTest.class.getName());
                                try {
                                    if (num >= 0) {
                                        System.out.println(Thread.currentThread().getName() + " | " + (num--));
                                    }
                                }
                                finally {
                                    LockUtils.unlock(ZkReentrantLockTest.class.getName());
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        ThreadUtils.sleepQuietly(5000);
    }

    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 1000000; j++) {
                            if (num >= 0) {
                                if (LockUtils.tryLock(ZkReentrantLockTest.class.getName(), 500, TimeUnit.MILLISECONDS)) {
                                    try {
                                        if (num >= 0) {
                                            System.out.println(Thread.currentThread().getName() + " | " + (num--));
                                        }
                                    }
                                    finally {
                                        LockUtils.unlock(ZkReentrantLockTest.class.getName());
                                    }
                                }
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        ThreadUtils.sleepQuietly(5000);
    }

}
