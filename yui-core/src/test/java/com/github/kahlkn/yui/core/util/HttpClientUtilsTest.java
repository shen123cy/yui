package com.github.kahlkn.yui.core.util;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilsTest {
    private String testUrl = "https://apyhs.blog/";
    private String testUrl1 = "https://www.taobao.com";
    private String testUrl2 = "https://www.baidu.com";

    @Test
    public void test1() throws IOException {
         System.out.println(HttpClientUtils.get(testUrl));
         System.out.println(HttpClientUtils.get(testUrl1));
        System.out.println(HttpClientUtils.get(testUrl2));
    }

    @Test
    public void test2() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("aaaa", "123");
        map.put("bbbb", "456");
        System.out.println(HttpClientUtils.post(testUrl, map));
    }

}
