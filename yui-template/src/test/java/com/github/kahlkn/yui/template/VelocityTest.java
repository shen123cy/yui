package com.github.kahlkn.yui.template;

import com.github.kahlkn.yui.template.support.VelocityAdapter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class VelocityTest {
    private String source = "This is test string \"${str}\", \nTest string is \"${str}\". ";
    private String source1 = "You name is \"${data.name}\", \nAnd you age is \"${data.age}\". ";
    private Map<String, Object> input = new HashMap<String, Object>();
    private TemplateEngine engine;

    @Before
    public void init() {
        input.put("str", "hello, world! ");
        input.put("nullVal", null);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "zhangsan");
        data.put("age", "19");
        input.put("data", data);
        engine = new TemplateEngine(new VelocityAdapter());
    }

    @Test
    public void test1() throws Exception {
        System.out.println(engine.renderToString(input, "source", source));
        System.out.println(engine.renderToString(input, "source1", source1));
        System.out.println();
        System.out.println(engine.renderToString("testVelocity.vm", input));
    }

}
