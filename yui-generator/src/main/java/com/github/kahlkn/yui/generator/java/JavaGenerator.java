package com.github.kahlkn.yui.generator.java;

import com.github.kahlkn.artoria.jdbc.DbUtils;
import com.github.kahlkn.yui.generator.GenerateException;
import com.github.kahlkn.yui.generator.common.AbstractGenerator;
import com.github.kahlkn.yui.generator.common.TableMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaGenerator extends AbstractGenerator {
    private DbUtils dbUtils;
    private Set<String> excludedTables = new HashSet<String>();
    private String[] removedTableNamePrefixes;
    private TypeMapper typeMapper = new TypeMapper();
    private List<TableMeta> tableMetas = new ArrayList<TableMeta>();

    public JavaGenerator(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public void generate() throws GenerateException {

    }

}
