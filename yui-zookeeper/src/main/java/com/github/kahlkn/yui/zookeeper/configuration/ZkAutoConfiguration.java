package com.github.kahlkn.yui.zookeeper.configuration;

import com.github.kahlkn.artoria.lock.LockUtils;
import com.github.kahlkn.artoria.util.StringUtils;
import com.github.kahlkn.yui.zookeeper.lock.ZkReentrantLock;
import com.github.kahlkn.yui.zookeeper.lock.ZkReentrantLockFactory;
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

    @Value("${zookeeper.url}")
    private String zkUrl;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isNotBlank(zkUrl)) {
            log.info("Find zookeeper url \"{}\". ", zkUrl);
            String url = zkUrl;
            if (url.startsWith(ZK_URL_PREFIX)) {
                url = url.substring(ZK_URL_PREFIX.length());
            }
            try {
                ZkReentrantLockFactory factory = new ZkReentrantLockFactory(url);
                LockUtils.registerFactory(ZkReentrantLock.class, factory);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        log.info("Can not find zookeeper url in key \"zookeeper.url\". ");
    }

    @Override
    public void destroy() throws Exception {
    }

}
