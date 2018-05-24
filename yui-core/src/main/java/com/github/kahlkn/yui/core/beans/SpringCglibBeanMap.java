package com.github.kahlkn.yui.core.beans;

import com.github.kahlkn.artoria.beans.BeanMap;
import com.github.kahlkn.artoria.converter.Converter;

import java.util.Set;

/**
 * Spring cglib bean map.
 * @author Kahle
 */
public class SpringCglibBeanMap extends BeanMap {

    private org.springframework.cglib.beans.BeanMap beanMap;

    @Override
    public void setBean(Object bean) {
        super.setBean(bean);
        beanMap = org.springframework.cglib.beans.BeanMap.create(bean);
    }

    @Override
    protected Object get(Object bean, Object key) {
        return beanMap.get(key);
    }

    @Override
    protected Object put(Object bean, Object key, Object value) {
        if (key != null) {
            Converter cvt = this.getConverter();
            Class type = beanMap.getPropertyType((String) key);
            value = cvt.convert(value, type);
        }
        return beanMap.put(key, value);
    }

    @Override
    public Set keySet() {
        return beanMap.keySet();
    }

}
