package com.github.kahlkn.yui.selenium;

import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kahle
 */
public class WebDriverPool {
    private final static Logger log = LoggerFactory.getLogger(WebDriverPool.class);
    private final static String REMOTE_WEB_URL_PROPERTY = "remote.web.url";
    private final static int DEFAULT_PAGE_LOAD_TIMEOUT = 5;
    private final static int DEFAULT_CAPACITY = 5;
    private final static int STAT_DESTROYED = 2;
    private final static int STAT_RUNNING = 1;

    public static WebDriverPool on(DriverType driverType, DesiredCapabilities dCaps) {
        return new WebDriverPool(driverType, dCaps, DEFAULT_CAPACITY, DEFAULT_PAGE_LOAD_TIMEOUT);
    }

    public static WebDriverPool on(DriverType driverType, DesiredCapabilities dCaps, int capacity, long pageLoadTimeout) {
        return new WebDriverPool(driverType, dCaps, capacity, pageLoadTimeout);
    }

    /**
     * store webDrivers created
     */
    private List<WebDriver> webDriverList = Collections.synchronizedList(new ArrayList<WebDriver>());
    /**
     * store webDrivers available
     */
    private BlockingDeque<WebDriver> innerQueue = new LinkedBlockingDeque<WebDriver>();
    private AtomicInteger stat = new AtomicInteger(STAT_RUNNING);
    private final DesiredCapabilities dCaps;
    private final DriverType driverType;
    private final int capacity;
    private final long pageLoadTimeout;

    private WebDriverPool(DriverType driverType, DesiredCapabilities dCaps, int capacity, long pageLoadTimeout) {
        this.driverType = driverType;
        this.dCaps = dCaps;
        this.capacity = capacity;
        this.pageLoadTimeout = pageLoadTimeout;
    }

    private void checkRunning() {
        if (!stat.compareAndSet(STAT_RUNNING, STAT_RUNNING)) {
            throw new IllegalStateException("Already destroyed!");
        }
    }

    private WebDriver createDriver() throws IOException {
        if (DriverType.REMOTE.equals(driverType)) {
            String urlString = (String) dCaps.getCapability(REMOTE_WEB_URL_PROPERTY);
            URL url;
            try {
                url = new URL(urlString);
            }
            catch (MalformedURLException mue) {
                throw new IllegalArgumentException("DesiredCapabilities object must have \""
                        + REMOTE_WEB_URL_PROPERTY + "\" property, if driver type is \"REMOTE\". ");
            }
//            if (StringUtils.isBlank(dCaps.getBrowserName())) {
//                dCaps.setBrowserName("phantomjs");
//            }
            log.info("Try create \"RemoteWebDriver\" instance. ");
            return new RemoteWebDriver(url, dCaps);
        }
        else {
            try {
                Class<? extends WebDriver> clz = driverType.getDriverClass();
                log.info("Try create \"" + clz.getSimpleName() + "\" instance. ");
                Constructor<? extends WebDriver> cst = clz.getConstructor(Capabilities.class);
                return cst.newInstance(dCaps);
            }
            catch (ReflectiveOperationException e) {
                throw new RuntimeException("Class \"" + driverType.getDriverClass()
                        + "\" and parameter type \"" + Capabilities.class + "\" create instance failure. ", e);
            }
        }
    }

    public WebDriver take() throws InterruptedException {
        checkRunning();
        WebDriver poll = innerQueue.poll();
        if (poll != null) {
            return poll;
        }
        if (webDriverList.size() < capacity) {
            synchronized (webDriverList) {
                if (webDriverList.size() < capacity) {
                    // add new WebDriver instance into pool
                    try {
                        WebDriver driver = createDriver();
                        WebDriver.Timeouts timeouts = driver.manage().timeouts();
                        timeouts.pageLoadTimeout(pageLoadTimeout, TimeUnit.MILLISECONDS);
                        innerQueue.add(driver);
                        webDriverList.add(driver);
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                    //
                }
            }
        }
        return innerQueue.take();
    }

    public void restore(WebDriver webDriver) {
        log.debug("Try restore WebDriver. ");
        checkRunning();
        innerQueue.add(webDriver);
        log.debug("Restore WebDriver success. ");
    }

    public void destroy() {
        log.debug("Try destroy WebDriverPool. ");
        boolean b = stat.compareAndSet(STAT_RUNNING, STAT_DESTROYED);
        if (!b) {
            throw new IllegalStateException("Already destroyed!");
        }
        for (WebDriver webDriver : webDriverList) {
            log.info("Quit webDriver" + webDriver);
            webDriver.quit();
            webDriver = null;
        }
        log.debug("Destroy WebDriverPool success. ");
    }

}
