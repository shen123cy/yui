package com.github.kahlkn.yui.generator;

import com.github.kahlkn.artoria.template.TemplateEngine;
import com.github.kahlkn.artoria.util.Assert;

public abstract class AbstractGenerator implements Generator {
    private TemplateEngine engine;

    {
        this.setEngine(ConfigManager.getDefaultEngine());
    }

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        Assert.notNull(engine, "Parameter \"engine\" must not null. ");
        this.engine = engine;
    }

}
