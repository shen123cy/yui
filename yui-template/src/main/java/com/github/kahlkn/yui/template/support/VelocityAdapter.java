package com.github.kahlkn.yui.template.support;

import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.yui.template.EngineAdapter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeSingleton;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import static com.github.kahlkn.artoria.util.Const.DEFAULT_CHARSET_NAME;
import static org.apache.velocity.app.Velocity.*;

/**
 * Velocity template engine adapter.
 * @author Kahle
 */
public class VelocityAdapter implements EngineAdapter {
    private static final String FILE_LOADER_CLASS = "file.resource.loader.class";
    private static final String CLASS_LOADER_CLASS = "class.resource.loader.class";
    private static final String JAR_LOADER_CLASS = "jar.resource.loader.class";
    private static final String FILE_MODIFY_CHECK_INTERVAL = "file.resource.loader.modificationCheckInterval";

    private static VelocityEngine defaultEngine = new VelocityEngine();

    static {
        Properties properties = new Properties();
        properties.setProperty(INPUT_ENCODING, DEFAULT_CHARSET_NAME);
        properties.setProperty(OUTPUT_ENCODING, DEFAULT_CHARSET_NAME);
        properties.setProperty(RESOURCE_LOADER, "file, class, jar");
        properties.setProperty(FILE_LOADER_CLASS, "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        properties.setProperty(CLASS_LOADER_CLASS, "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty(JAR_LOADER_CLASS, "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
        properties.setProperty(FILE_RESOURCE_LOADER_CACHE, "true");
        properties.setProperty(FILE_MODIFY_CHECK_INTERVAL, "86400");
        defaultEngine.init(properties);
    }

    private Boolean isInit = false;
    private VelocityEngine engine;

    public VelocityAdapter() {
        isInit = RuntimeSingleton.getRuntimeServices().isInitialized();
    }

    public VelocityAdapter(VelocityEngine engine) {
        Assert.notNull(engine, "Parameter \"engine\" must not null. ");
        this.engine = engine;
    }

    private Context handleContext(Object data) {
        Context context;
        if (data instanceof Context) {
            context = (Context) data;
        }
        else if (data instanceof Map) {
            Map model = (Map) data;
            context = new VelocityContext();
            for (Object key : model.keySet()) {
                Object val = model.get(key);
                context.put((String) key, val);
            }
        }
        else {
            throw new VelocityException("Parameter \"data\" cannot handle. ");
        }
        return context;
    }

    @Override
    public void render(String name, String encoding, Object data, Writer writer) throws Exception {
        Context context = this.handleContext(data);
        if (isInit) {
            Velocity.mergeTemplate(name, encoding, context, writer);
        }
        else if (engine != null) {
            engine.mergeTemplate(name, encoding, context, writer);
        }
        else {
            defaultEngine.mergeTemplate(name, encoding, context, writer);
        }
    }

    @Override
    public void render(Object data, Writer writer, String logTag, Reader reader) throws Exception {
        Context context = this.handleContext(data);
        if (isInit) {
            Velocity.evaluate(context, writer, logTag, reader);
        }
        else if (engine != null) {
            engine.evaluate(context, writer, logTag, reader);
        }
        else {
            defaultEngine.evaluate(context, writer, logTag, reader);
        }
    }

}
