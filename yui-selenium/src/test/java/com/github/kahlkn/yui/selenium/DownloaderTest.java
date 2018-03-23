package com.github.kahlkn.yui.selenium;

import org.junit.Test;
import us.codecraft.xsoup.XElements;
import us.codecraft.xsoup.Xsoup;

public class DownloaderTest {

    @Test
    public void htmlunitTest() throws Exception {
        Downloader htmlunit = Downloader.on("htmlunit", 5000);
        // String download = htmlunit.setSleepTime(9000).download("http://huaban.com/");
        // String download = htmlunit.setSleepTime(9000).download("https://www.lup2p.com/lup2p/");
        // String download = htmlunit.setSleepTime(9000).download("http://www.baidu.com");
        // String download = htmlunit.setSleepTime(9000).download("https://cn.bing.com");
        String download = htmlunit.setSleepTime(15000).download("https://github.com/kahlkn");
//        XElements select = Xsoup.select(download, "//*[@id=\"page\"]/div[1]/div[3]/div[2]/div[2]");
        System.out.println(download);
        System.out.println();
        System.out.println();
//        System.out.println(select.get());
        htmlunit.close();
    }

    @Test
    public void phantomjsTest() throws Exception {
        // Downloader phantomjs = Downloader.phantomjs("D:\\Kit\\WebDriver\\phantomjs.exe", 2);
        // Downloader phantomjs = Downloader.on("D:\\Kit\\WebDriver\\phantomjs.exe");
        // Downloader phantomjs = Downloader.on(PhantomJSDriverHelper.executable().toString());
        Downloader phantomjs = Downloader.on(DriverType.PHANTOMJS, null, 30000);
        try {
            String download = phantomjs.setSleepTime(9000).download("http://huaban.com/");
            XElements select = Xsoup.select(download, "//*[@id=\"page\"]/div[1]/div[3]/div[2]/div[2]");
            System.out.println(download);
            System.out.println();
            System.out.println();
            System.out.println(select.get());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            phantomjs.close();
        }
    }

    @Test
    public void chromeTest() throws Exception {
        // Downloader chrome = Downloader.on("D:\\Kit\\WebDriver\\chromedriver.exe", 2);
        Downloader chrome = Downloader.on("D:\\Kit\\WebDriver\\chromedriver.exe", 5000);
        String download = chrome.setSleepTime(9000).download("http://huaban.com/");
        XElements select = Xsoup.select(download, "//*[@id=\"page\"]/div[1]/div[3]/div[2]/div[2]");
        System.out.println(select.get());
        chrome.close();
    }

}
