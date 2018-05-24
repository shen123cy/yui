package com.github.kahlkn.yui.core.beans;

import com.github.kahlkn.artoria.beans.BeanCopier;
import com.github.kahlkn.artoria.converter.Converter;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.artoria.util.CollectionUtils;
import com.github.kahlkn.artoria.util.StringUtils;

import java.util.List;

import static com.github.kahlkn.artoria.util.Const.GET_OR_SET_LENGTH;

/**
 * Spring cglib bean copier.
 * @author Kahle
 */
public class SpringCglibBeanCopier implements BeanCopier {

    private static class SpringCglibConverterAdapter implements org.springframework.cglib.core.Converter {
        private Converter converter;
        private List<String> ignoreProperties;
        private boolean hasIgnore;

        public void setConverter(Converter converter) {
            Assert.notNull(converter, "Parameter \"converter\" must not null. ");
            this.converter = converter;
        }

        public SpringCglibConverterAdapter(Converter converter, List<String> ignoreProperties) {
            this.setConverter(converter);
            this.ignoreProperties = ignoreProperties;
            this.hasIgnore = CollectionUtils.isNotEmpty(ignoreProperties);
        }

        @Override
        public Object convert(Object value, Class valClass, Object methodName) {
            // methodName must be setter, because cglib already processed.
            if (hasIgnore && ignoreProperties.contains(
                    StringUtils.uncapitalize(((String) methodName).substring(GET_OR_SET_LENGTH))
            )) {
                return null;
            }
            return converter.convert(value, valClass);
        }

    }

    @Override
    public void copy(Object from, Object to, List<String> ignoreProperties, Converter converter) {
        Class<?> fromClass = from.getClass();
        Class<?> toClass = to.getClass();
        org.springframework.cglib.beans.BeanCopier copier =
                org.springframework.cglib.beans.BeanCopier.create(fromClass, toClass, true);
        SpringCglibConverterAdapter adapter = new SpringCglibConverterAdapter(converter, ignoreProperties);
        copier.copy(from, to, adapter);
    }

}
