package com.github.kahlkn.yui.generator.java;

import java.util.HashMap;
import java.util.Map;

public class TypeMapper {
    private Map<String, String> map = new HashMap<String, String>();

    public TypeMapper() {
        // java.util.Data can not be returned
        // java.sql.Date, java.sql.Time, java.sql.Timestamp all extends java.util.Data so getDate can return the three types data
        map.put("java.util.Date", "java.util.Date");

        // date, year
        map.put("java.sql.Date", "java.util.Date");

        // time
        map.put("java.sql.Time", "java.util.Date");

        // timestamp, datetime
        map.put("java.sql.Timestamp", "java.util.Date");

        // binary, varbinary, tinyblob, blob, mediumblob, longblob
        // qjd project: print_info.content varbinary(61800);
        map.put("[B", "byte[]");

        // varchar, char, enum, set, text, tinytext, mediumtext, longtext
        map.put("java.lang.String", "java.lang.String");

        // int, integer, tinyint, smallint, mediumint
        map.put("java.lang.Integer", "java.lang.Integer");

        // bigint
        map.put("java.lang.Long", "java.lang.Long");

        // real, double
        map.put("java.lang.Double", "java.lang.Double");

        // float
        map.put("java.lang.Float", "java.lang.Float");

        // bit
        map.put("java.lang.Boolean", "java.lang.Boolean");

        // decimal, numeric
        map.put("java.math.BigDecimal", "java.math.BigDecimal");

        // unsigned bigint
        map.put("java.math.BigInteger", "java.math.BigInteger");

        // short
        map.put("java.lang.Short", "java.lang.Short");
    }

    public String getType(String columnClassName) {
        return map.get(columnClassName);
    }

    public void unregister(String columnClassName) {
        map.remove(columnClassName);
    }

    public void register(String columnClassName, String javaClassName) {
        map.put(columnClassName, javaClassName);
    }

}
