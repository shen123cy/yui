package com.github.kahlkn.yui.generator;

import com.github.kahlkn.yui.template.TemplateEngine;

/**
 * Code generator interface.
 * @author Kahle
 */
public interface Generator {

    TemplateEngine getTemplateEngine();

    void setTemplateEngine(TemplateEngine templateEngine);

    void generate() throws GenerateException;

}
