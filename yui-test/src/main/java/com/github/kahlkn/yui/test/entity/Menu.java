package com.github.kahlkn.yui.test.entity;

import java.util.List;

/**
 * Test entity, one module mapping more menu
 * , one menu mapping more module.
 * @author Kahle
 */
public class Menu {
    private String module;
    private Integer id;
    private String code;
    private String name;
    private String parentCode;
    private List<Menu> sonMenuList;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<Menu> getSonMenuList() {
        return sonMenuList;
    }

    public void setSonMenuList(List<Menu> sonMenuList) {
        this.sonMenuList = sonMenuList;
    }

}
