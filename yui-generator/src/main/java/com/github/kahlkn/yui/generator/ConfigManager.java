package com.github.kahlkn.yui.generator;

import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import com.github.kahlkn.artoria.template.TemplateEngine;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.yui.template.VelocityAdapter;

public class ConfigManager {
    private static Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private static TemplateEngine defaultEngine;

    static {
        TemplateEngine engine = new TemplateEngine(new VelocityAdapter());
        ConfigManager.setDefaultEngine(engine);
    }

    public static TemplateEngine getDefaultEngine() {
        return defaultEngine;
    }

    public static void setDefaultEngine(TemplateEngine defaultEngine) {
        Assert.notNull(defaultEngine, "Parameter \"defaultEngine\" must not null. ");
        ConfigManager.defaultEngine = defaultEngine;
        String name = defaultEngine.getAdapter().getClass().getName();
        log.info("Default template engine is: " + name);
    }

}
