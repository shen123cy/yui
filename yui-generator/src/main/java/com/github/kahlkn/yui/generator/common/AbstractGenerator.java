package com.github.kahlkn.yui.generator.common;

import com.github.kahlkn.yui.generator.Generator;
import com.github.kahlkn.yui.template.TemplateEngine;
import com.github.kahlkn.yui.template.TemplateUtils;

public abstract class AbstractGenerator implements Generator {
    private TemplateEngine templateEngine;

    @Override
    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    @Override
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

}
