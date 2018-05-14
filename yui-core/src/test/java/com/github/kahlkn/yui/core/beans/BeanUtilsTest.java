package com.github.kahlkn.yui.core.beans;

import com.alibaba.fastjson.JSON;
import com.github.kahlkn.artoria.beans.BeanUtils;
import com.github.kahlkn.artoria.beans.CglibBeanCopier;
import com.github.kahlkn.artoria.util.RandomUtils;
import com.github.kahlkn.yui.test.entities.Person;
import com.github.kahlkn.yui.test.entities.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BeanUtilsTest {
    private static Person person = RandomUtils.nextObject(Person.class);
    private static Map<String, Object> personMap = BeanUtils.beanToMap(RandomUtils.nextObject(Person.class));

    @Test
    public void testIgnoreCglibCopy() {
        BeanUtils.setBeanCopier(new CglibBeanCopier());
        List<String> ignore = new ArrayList<String>();
        Collections.addAll(ignore, "name", "age", "123test");
        Student student = new Student();
        BeanUtils.copy(person, student, ignore);
        System.out.println(JSON.toJSONString(student));
    }

}
