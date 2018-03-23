package com.github.kahlkn.yui.template;

import com.github.kahlkn.artoria.io.StringBuilderWriter;
import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import com.github.kahlkn.artoria.util.Assert;

import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import static com.github.kahlkn.artoria.util.Const.DEFAULT_CHARSET_NAME;

/**
 * Template engine tools.
 * @author Kahle
 */
public class TemplateUtils {
    private static Logger log = LoggerFactory.getLogger(TemplateUtils.class);
    private static TemplateAdapter adapter;

    static {
        TemplateUtils.setAdapter(new VelocityAdapter());
    }

    public static TemplateAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(TemplateAdapter adapter) {
        Assert.notNull(adapter, "Parameter \"adapter\" must not null. ");
        TemplateUtils.adapter = adapter;
        log.info("Set template adapter: " + adapter.getClass().getName());
    }

    public static void render(String name, Object data, Writer writer) throws Exception {
        TemplateUtils.render(name, DEFAULT_CHARSET_NAME, data, writer);
    }

    public static void render(String name, String encoding, Object data, Writer writer) throws Exception {
        adapter.render(name, encoding, data, writer);
    }

    public static void render(Object data, Writer writer, String logTag, String template) throws Exception {
        Assert.notBlank(template, "Parameter \"template\" must not blank. ");
        StringReader reader = new StringReader(template);
        TemplateUtils.render(data, writer, logTag, reader);
    }

    public static void render(Object data, Writer writer, String logTag, Reader reader) throws Exception {
        adapter.render(data, writer, logTag, reader);
    }

    public static String renderToString(String name, Object data) throws Exception {
        return TemplateUtils.renderToString(name, DEFAULT_CHARSET_NAME, data);
    }

    public static String renderToString(String name, String encoding, Object data) throws Exception {
        StringBuilderWriter writer = new StringBuilderWriter();
        adapter.render(name, encoding, data, writer);
        return writer.toString();
    }

    public static String renderToString(Object data, String logTag, String template) throws Exception {
        Assert.notBlank(template, "Parameter \"template\" must not blank. ");
        StringReader reader = new StringReader(template);
        return TemplateUtils.renderToString(data, logTag, reader);
    }

    public static String renderToString(Object data, String logTag, Reader reader) throws Exception {
        StringBuilderWriter writer = new StringBuilderWriter();
        adapter.render(data, writer, logTag, reader);
        return writer.toString();
    }

}
