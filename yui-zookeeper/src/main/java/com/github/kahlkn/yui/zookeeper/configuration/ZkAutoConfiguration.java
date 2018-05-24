package com.github.kahlkn.yui.zookeeper.configuration;

import com.github.kahlkn.artoria.lock.LockUtils;
import com.github.kahlkn.artoria.util.StringUtils;
import com.github.kahlkn.yui.zookeeper.lock.ZkReentrantLock;
import com.github.kahlkn.yui.zookeeper.lock.ZkReentrantLockFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Zookeeper auto configuration.
 * @author Kahle
 */
@Configuration
public class ZkAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(ZkAutoConfiguration.class);
    private static final String ZK_URL_PREFIX = "zookeeper://";
    private CuratorFramework curator;

    @Value("${zookeeper.url}")
    private String zkUrl;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.handleZookeeperUrl();
    }

    @Override
    public void destroy() throws Exception {
        if (curator != null) {
            curator.close();
            log.info("Release curator object \"{}\" success. ", curator);
        }
    }

    /**
     * Handle key "zookeeper.url" in configuration file.
     * @return Judge curator object initialize success
     */
    private boolean handleZookeeperUrl() {
        if (StringUtils.isNotBlank(zkUrl)) {
            log.info("Find zookeeper url \"{}\". ", zkUrl);
            String url = zkUrl;
            if (url.startsWith(ZK_URL_PREFIX)) {
                url = url.substring(ZK_URL_PREFIX.length());
            }
            try {
                curator = CuratorFrameworkFactory.builder()
                        .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                        .sessionTimeoutMs(30000)
                        .connectionTimeoutMs(30000)
                        .connectString(url)
                        .build();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                return false;
            }
            ZkReentrantLockFactory factory = new ZkReentrantLockFactory(curator);
            LockUtils.registerFactory(ZkReentrantLock.class, factory);
            return true;
        }
        log.info("Can not find zookeeper url by key \"zookeeper.url\". ");
        return false;
    }

}
