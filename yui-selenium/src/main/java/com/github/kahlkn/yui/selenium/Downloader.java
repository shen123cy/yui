package com.github.kahlkn.yui.selenium;

import ch.racic.selenium.drivers.PhantomJSDriverHelper;
import ch.racic.selenium.drivers.exceptions.ExecutableNotFoundException;
import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import com.github.kahlkn.artoria.util.ClassUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Kahle
 */
public class Downloader {
    private static final String RACIC_PHANTOMJS_DRIVER_HELPER = "ch.racic.selenium.drivers.PhantomJSDriverHelper";
    private static final Logger log = LoggerFactory.getLogger(Downloader.class);
    private static final int DEFAULT_POOL_SIZE = 1;

    public static Downloader on(String driverPath, long pageLoadTimeout) {
        return on(driverPath, DEFAULT_POOL_SIZE, pageLoadTimeout);
    }

    public static Downloader on(String driverPath, int poolSize, long pageLoadTimeout) {
        DriverType driverType = DriverType.find(driverPath);
        if (driverType == null) {
            throw new RuntimeException("Can not match any driver type. ");
        }
        else {
            log.info("Downloader match \"" + driverType.getDriverClass().getSimpleName() + "\" driver type. ");
        }
        return on(driverType, driverPath, poolSize, pageLoadTimeout);
    }

    public static Downloader on(DriverType driverType, String driverPath, long pageLoadTimeout) {
        return on(driverType, driverPath, DEFAULT_POOL_SIZE, pageLoadTimeout);
    }

    public static Downloader on(DriverType driverType, String driverPath, int poolSize, long pageLoadTimeout) {
        WebDriverPool webDriverPool;
        if (DriverType.HTMLUNIT.equals(driverType)) {
            webDriverPool = htmlunit(poolSize, pageLoadTimeout);
        }
        else if (DriverType.PHANTOMJS.equals(driverType)) {
            webDriverPool = phantomjs(driverPath, poolSize, pageLoadTimeout);
        }
        else if (DriverType.CHROME.equals(driverType)) {
            webDriverPool = chrome(driverPath, poolSize, pageLoadTimeout);
        }
        else if (DriverType.FIREFOX.equals(driverType)) {
//            return FirefoxDriver.BINARY;
            throw new RuntimeException("Not finish. ");
        }
        else if (DriverType.EDGE.equals(driverType)) {
//            return EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY;
            throw new RuntimeException("Not finish. ");
        }
        else if (DriverType.IE.equals(driverType)) {
//            return InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY;
            throw new RuntimeException("Not finish. ");
        }
        else if (DriverType.OPERA.equals(driverType)) {
//            return OperaDriverService.OPERA_DRIVER_EXE_PROPERTY;
            throw new RuntimeException("Not finish. ");
        }
        else if (DriverType.SAFARI.equals(driverType)) {
            throw new RuntimeException("Not finish. ");
        }
        else if (DriverType.REMOTE.equals(driverType)) {
            throw new RuntimeException("Not finish. ");
        }
        else {
            throw new RuntimeException("Unexpected enum object. ");
        }
        return new Downloader(webDriverPool);
    }

    public static Downloader on(DriverType driverType, DesiredCapabilities dCaps, int poolSize, long pageLoadTimeout) {
        return new Downloader(WebDriverPool.on(driverType, dCaps, poolSize, pageLoadTimeout));
    }

    private static WebDriverPool htmlunit(int poolSize, long pageLoadTimeout) {
        DesiredCapabilities htmlunit = DesiredCapabilities.htmlUnit();
        htmlunit.setJavascriptEnabled(true);
        htmlunit.setBrowserName("htmlunit");
        return WebDriverPool.on(DriverType.HTMLUNIT, htmlunit, poolSize, pageLoadTimeout);
    }

    private static WebDriverPool phantomjs(String driverPath, int poolSize, long pageLoadTimeout) {
        if (StringUtils.isBlank(driverPath)) {
            if (ClassUtils.isPresent(RACIC_PHANTOMJS_DRIVER_HELPER, Downloader.class.getClassLoader())) {
                try {
                    driverPath = PhantomJSDriverHelper.executable().toString();
                }
                catch (ExecutableNotFoundException e) {
                    throw new RuntimeException("Input driver path is blank" +
                            ", and run \"PhantomJSDriverHelper.executable()\" failure. ");
                }
                catch (IOException e) {
                    throw new RuntimeException("Input driver path is blank" +
                            ", and run \"PhantomJSDriverHelper.executable()\" failure. ");
                }
            }
            else {
                throw new RuntimeException("Input driver path is blank. ");
            }
        }
        String extension = FilenameUtils.getExtension(driverPath);
        boolean isGhostDriver = "js".equals(extension);
        DesiredCapabilities phantomjs = DesiredCapabilities.phantomjs();
        phantomjs.setJavascriptEnabled(true);
        ArrayList<String> cliArgsCap = new ArrayList<String>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        phantomjs.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        String[] log = {"--logLevel=INFO"};
        phantomjs.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, log);
        phantomjs.setCapability("takesScreenshot", false);
        String pathProp = PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY;
        pathProp = isGhostDriver ? PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY : pathProp;
        phantomjs.setCapability(pathProp, driverPath);
        return WebDriverPool.on(DriverType.PHANTOMJS, phantomjs, poolSize, pageLoadTimeout);
    }

    private static WebDriverPool chrome(String driverPath, int poolSize, long pageLoadTimeout) {
        DesiredCapabilities chrome = DesiredCapabilities.chrome();
        chrome.setJavascriptEnabled(true);
        chrome.setCapability("takesScreenshot", false);
        // The path to the driver executable must be set by the webdriver.chrome.driver system property;
        String pathProp = ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;
        chrome.setCapability(pathProp, driverPath);
        System.setProperty(pathProp, driverPath);
        return WebDriverPool.on(DriverType.CHROME, chrome, poolSize, pageLoadTimeout);
    }

    private int sleepTime = 0;

    private final WebDriverPool webDriverPool;

    private Downloader(WebDriverPool webDriverPool) {
        this.webDriverPool = webDriverPool;
    }

    public WebDriverPool getWebDriverPool() {
        return webDriverPool;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public Downloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public String download(String url) {
        return download(url, null);
    }

    public String download(String url, Map<String, String> cookies) {
        WebDriver webDriver;
        try {
            webDriver = webDriverPool.take();
        } catch (InterruptedException e) {
            log.warn("Take WebDriver Failure. ", e);
            return null;
        }
        try {
            log.info("Downloading \"" + url + "\" page. ");
            webDriver.get(url);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.warn("Thread sleep failure, will running continue. ", e);
            }

            // Cookies
            WebDriver.Options manage = webDriver.manage();
            if (MapUtils.isNotEmpty(cookies)) {
                log.info("Has cookies, try assembly. ");
                log.debug("Cookies \"" + cookies + "\". ");
                for (Map.Entry<String, String> entry : cookies.entrySet()) {
                    Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
                    manage.addCookie(cookie);
                }
            }

            // Contents
            WebElement webElement = webDriver.findElement(By.xpath("/html"));
            String content = webElement.getAttribute("outerHTML");
            log.info("Downloading page success. ");
            log.debug("The page content : " + content);
            // clear cookies and restore pool
            manage.deleteAllCookies();
            return content;
        }
        finally {
            webDriverPool.restore(webDriver);
        }
    }

    public void close() {
        webDriverPool.destroy();
    }

}
