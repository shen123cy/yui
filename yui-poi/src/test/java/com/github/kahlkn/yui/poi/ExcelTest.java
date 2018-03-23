package com.github.kahlkn.yui.poi;

import com.github.kahlkn.yui.test.entities.Student;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelTest {

    @Test
    public void test1() throws Exception {
        Excel excel = ExcelUtils.createByFile(new File("e:\\123.xlsx"));
        excel.selectSheet(0);
        List<Student> students = ExcelUtils.readToBeans(excel, Student.class);
        System.out.println(students);
    }

    @Test
    public void test2() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("You Name", "name");
        map.put("You Age", "age");
        map.put("You Email", "email");
        map.put("You Blog", "blog");
        Excel excel = ExcelUtils.createByFile(new File("e:\\123.xlsx"));
        excel.selectSheet(0);
        List<Student> students = ExcelUtils.readToBeans(excel, Student.class, map);
        System.out.println(students);
    }

}
