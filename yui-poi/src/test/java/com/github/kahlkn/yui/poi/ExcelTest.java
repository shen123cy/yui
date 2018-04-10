package com.github.kahlkn.yui.poi;

import com.alibaba.fastjson.JSON;
import com.github.kahlkn.artoria.util.RandomUtils;
import com.github.kahlkn.yui.test.entities.Student;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class ExcelTest {
    private static Map<String, String> titleStuMap = new HashMap<String, String>();
    private static Map<String, String> stuTitleMap = new HashMap<String, String>();
    private static List<Student> students = RandomUtils.nextList(Student.class);

    static {
        titleStuMap.put("Student Name", "name");
        titleStuMap.put("Student Age", "age");
        titleStuMap.put("Student Height", "height");
        titleStuMap.put("Student Id", "studentId");
        titleStuMap.put("School Name", "schoolName");
        for (Map.Entry<String, String> entry : titleStuMap.entrySet()) {
            stuTitleMap.put(entry.getValue(), entry.getKey());
        }
    }

    @Test
    public void test1() throws Exception {
        Excel excel = Excel.create(new File("e:\\123.xlsx"));
        List<Student> students = excel.readToBeans(Student.class, null, 4, 5);
        System.out.println(JSON.toJSONString(students, true));
    }

    @Test
    public void test2() throws Exception {
        Excel excel = Excel.create(new File("e:\\123_1.xlsx"));
        List<Student> students = excel.readToBeans(Student.class, titleStuMap, 3, 2);
        System.out.println(JSON.toJSONString(students, true));
    }

    @Test
    public void test3() throws Exception {
        System.out.println(JSON.toJSONString(students, true));
        Excel excel = Excel.create(null, Excel.XLSX, students, stuTitleMap, 3, 4);
        excel.write(new File("e:\\456.xlsx"));
    }

    @Test
    public void test4() throws Exception {
        System.out.println(JSON.toJSONString(students, true));
        Excel template = Excel.create(new File("e:\\template.xlsx"));
        Excel excel = Excel.create(template, Excel.XLSX, students, stuTitleMap, 3, 4);
        excel.write(new File("e:\\456.xlsx"));
    }

}
