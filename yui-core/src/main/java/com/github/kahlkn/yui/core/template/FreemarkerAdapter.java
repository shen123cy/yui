package com.github.kahlkn.yui.core.template;

import com.github.kahlkn.artoria.exception.UncheckedException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.Reader;
import java.io.Writer;

import static com.github.kahlkn.artoria.util.Const.DOT;
import static com.github.kahlkn.artoria.util.Const.SLASH;

/**
 * Freemarker template engine adapter.
 * @author Kahle
 */
public class FreemarkerAdapter implements TemplateAdapter {
    private Configuration configuration;

    public FreemarkerAdapter() {
        try {
            Configuration configuration = new Configuration();
            TemplateLoader[] loaders = new TemplateLoader[2];
            loaders[0] = new FileTemplateLoader(new File(DOT));
            loaders[1] = new ClassTemplateLoader(FreemarkerAdapter.class, SLASH);
            MultiTemplateLoader loader = new MultiTemplateLoader(loaders);
            configuration.setTemplateLoader(loader);
            this.configuration = configuration;
        }
        catch (Exception e) {
            throw new UncheckedException(e);
        }
    }

    public FreemarkerAdapter(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void render(String name, String encoding, Object data, Writer writer) throws Exception {
        Template template = configuration.getTemplate(name, encoding);
        template.process(data, writer);
    }

    @Override
    public void render(Object data, Writer writer, String logTag, Reader reader) throws Exception {
        Template template = new Template(logTag, reader, configuration);
        template.process(data, writer);
    }

}
