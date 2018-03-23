package com.github.kahlkn.yui.core.util;

import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CsvUtilsTest {

    @Test
    public void test1() throws Exception {
        List<List<String>> data = CsvUtils.readFile(new File("E:\\123.csv"), "GBK");
        for (List<String> list : data) {
            System.out.println(list);
        }
    }

    @Test
    public void test2() throws Exception {
        List<Map<String, Object>> mapList = CsvUtils.readFileToMap(new File("E:\\123.csv"), "GBK");
        for (Map<String, Object> map : mapList) {
            System.out.println(map);
        }
    }

    @Test
    public void test3() throws Exception {
        List<Map<String, Object>> mapList = CsvUtils.readFileToMap(new File("E:\\123.csv"), "GBK");
        for (Map<String, Object> map : mapList) {
            System.out.println(map);
        }

        CsvUtils.writeToFileByMap(mapList, new File("E:\\456.csv"), "GBK");
    }

}
