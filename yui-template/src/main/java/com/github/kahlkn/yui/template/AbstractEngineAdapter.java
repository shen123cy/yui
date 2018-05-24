package com.github.kahlkn.yui.template;

import com.github.kahlkn.artoria.io.StringBuilderWriter;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.yui.template.TemplateEngine;

import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import static com.github.kahlkn.artoria.util.Const.DEFAULT_CHARSET_NAME;

/**
 * Abstract template engine adapter.
 * @author Kahle
 */
public abstract class AbstractEngineAdapter implements TemplateEngine {

    @Override
    public void render(String name, Object data, Writer writer) throws Exception {
        this.render(name, DEFAULT_CHARSET_NAME, data, writer);
    }

    @Override
    public void render(Object data, Writer writer, String logTag, String template) throws Exception {
        Assert.notBlank(template, "Parameter \"template\" must not blank. ");
        StringReader reader = new StringReader(template);
        this.render(data, writer, logTag, reader);
    }

    @Override
    public String renderToString(String name, Object data) throws Exception {
        return this.renderToString(name, DEFAULT_CHARSET_NAME, data);
    }

    @Override
    public String renderToString(String name, String encoding, Object data) throws Exception {
        StringBuilderWriter writer = new StringBuilderWriter();
        this.render(name, encoding, data, writer);
        return writer.toString();
    }

    @Override
    public String renderToString(Object data, String logTag, String template) throws Exception {
        Assert.notBlank(template, "Parameter \"template\" must not blank. ");
        StringReader reader = new StringReader(template);
        return this.renderToString(data, logTag, reader);
    }

    @Override
    public String renderToString(Object data, String logTag, Reader reader) throws Exception {
        StringBuilderWriter writer = new StringBuilderWriter();
        this.render(data, writer, logTag, reader);
        return writer.toString();
    }

}
