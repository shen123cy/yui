package com.github.kahlkn.yui.zookeeper.lock;

import com.github.kahlkn.artoria.lock.LockException;
import com.github.kahlkn.artoria.lock.LockFactory;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.artoria.util.Hook;
import com.github.kahlkn.artoria.util.HookUtils;
import com.github.kahlkn.yui.zookeeper.ZkConnectionStateListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

import static com.github.kahlkn.artoria.util.Const.SLASH;

/**
 * Zookeeper reentrant mutex lock factory.
 * @author Kahle
 */
public class ZkReentrantLockFactory implements LockFactory {
    private static Logger log = LoggerFactory.getLogger(ZkReentrantLockFactory.class);
    private static final String CLASS_NAME = ZkReentrantLock.class.getName();
    private static final String REENTRANT_LOCK = "/lock/mutex/reentrant";
    private final CuratorFramework curator;

    public ZkReentrantLockFactory(String connectString) {
        Assert.notBlank(connectString, "Parameter \"connectString\" must not blank. ");
        curator = CuratorFrameworkFactory.builder()
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .connectString(connectString)
                .build();
        ConnectionStateListener listener = new ZkConnectionStateListener("ZK-" + connectString);
        curator.getConnectionStateListenable().addListener(listener);
        curator.start();
        log.info("Initialize ZkReentrantLockFactory success, and zookeeper connect address is \"{}\".", connectString);
        HookUtils.addShutdownHook(new Hook() {
            @Override
            public void call() {
                if (curator == null) { return; }
                curator.close();
                System.out.println("Release curator object \"" + curator + "\" success in shutdown hook. ");
            }
        });
    }

    public ZkReentrantLockFactory(CuratorFramework curator) {
        Assert.notNull(curator, "Parameter \"curator\" must not null. ");
        this.curator = curator;
    }

    @Override
    public Lock getInstance(String lockName, Class<? extends Lock> lockClass) {
        Assert.notBlank(lockName, "Parameter \"lockName\" must not blank. ");
        Assert.notNull(lockClass, "Parameter \"lockClass\" must not null. ");
        if (!ZkReentrantLock.class.isAssignableFrom(lockClass)) {
            throw new LockException("Parameter \"lockClass\" must assignable to \"" + CLASS_NAME + "\". ");
        }
        String path = REENTRANT_LOCK + SLASH + lockName;
        InterProcessMutex mutexLock = new InterProcessMutex(curator, path);
        return new ZkReentrantLock(mutexLock);
    }

}
