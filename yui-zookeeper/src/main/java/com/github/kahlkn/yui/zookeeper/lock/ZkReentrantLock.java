package com.github.kahlkn.yui.zookeeper.lock;

import com.github.kahlkn.artoria.lock.LockException;
import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import com.github.kahlkn.artoria.util.Assert;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Zookeeper reentrant mutex lock.
 * @author Kahle
 */
public class ZkReentrantLock implements Lock {
    private static Logger log = LoggerFactory.getLogger(ZkReentrantLock.class);
    private final InterProcessMutex mutexLock;
    private final Sync sync = new Sync();
    private long zkTimeout = 500;
    private TimeUnit zkTimeUnit = TimeUnit.MILLISECONDS;

    public ZkReentrantLock(InterProcessMutex mutexLock) {
        Assert.notNull(mutexLock, "Parameter \"mutexLock\" must not null. ");
        this.mutexLock = mutexLock;
    }

    public long getZkTimeout() {
        return zkTimeout;
    }

    public TimeUnit getZkTimeUnit() {
        return zkTimeUnit;
    }

    public void setZkTimeout(long zkTimeout, TimeUnit zkTimeUnit) {
        Assert.state(zkTimeout > 0, "Parameter \"zkTimeout\" must greater than 0. ");
        Assert.notNull(zkTimeUnit, "Parameter \"zkTimeUnit\" must not null. ");
        this.zkTimeout = zkTimeout;
        this.zkTimeUnit = zkTimeUnit;
    }

    @Override
    public void lock() {
        try {
            mutexLock.acquire();
        }
        catch (Exception e) {
            throw new LockException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    private class Sync extends AbstractQueuedSynchronizer {

        ConditionObject newCondition() {
            return new ConditionObject();
        }

        @Override
        protected boolean isHeldExclusively() {
            return this.getExclusiveOwnerThread() == Thread.currentThread();
        }

        @Override
        protected boolean tryAcquire(int arg) {
            try {
                return mutexLock.acquire(zkTimeout, zkTimeUnit);
            }
            catch (Exception e) {
                throw new LockException(e);
            }
        }

        @Override
        protected boolean tryRelease(int arg) {
            try {
                mutexLock.release();
                return true;
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }

    }

}
