package com.github.kahlkn.yui.template;

import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.yui.template.support.VelocityAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.Writer;

/**
 * Template engine tools.
 * @author Kahle
 */
public class TemplateUtils {
    private static Logger log = LoggerFactory.getLogger(TemplateUtils.class);
    private static TemplateEngine templateEngine;

    static {
        TemplateUtils.setTemplateEngine(new VelocityAdapter());
    }

    public static TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public static void setTemplateEngine(TemplateEngine templateEngine) {
        Assert.notNull(templateEngine, "Parameter \"templateEngine\" must not null. ");
        TemplateUtils.templateEngine = templateEngine;
        log.info("Set template engine: " + templateEngine.getClass().getName());
    }

    public static void render(String name, Object data, Writer writer) throws Exception {
        templateEngine.render(name, data, writer);
    }

    public static void render(String name, String encoding, Object data, Writer writer) throws Exception {
        templateEngine.render(name, encoding, data, writer);
    }

    public static void render(Object data, Writer writer, String logTag, String template) throws Exception {
        templateEngine.render(data, writer, logTag, template);
    }

    public static void render(Object data, Writer writer, String logTag, Reader reader) throws Exception {
        templateEngine.render(data, writer, logTag, reader);
    }

    public static String renderToString(String name, Object data) throws Exception {
        return templateEngine.renderToString(name, data);
    }

    public static String renderToString(String name, String encoding, Object data) throws Exception {
        return templateEngine.renderToString(name, encoding, data);
    }

    public static String renderToString(Object data, String logTag, String template) throws Exception {
        return templateEngine.renderToString(data, logTag, template);
    }

    public static String renderToString(Object data, String logTag, Reader reader) throws Exception {
        return templateEngine.renderToString(data, logTag, reader);
    }

}
