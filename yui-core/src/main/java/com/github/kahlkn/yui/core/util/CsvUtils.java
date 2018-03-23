package com.github.kahlkn.yui.core.util;

import com.github.kahlkn.artoria.io.IOUtils;
import com.github.kahlkn.artoria.io.StringBuilderWriter;
import com.github.kahlkn.artoria.util.CollectionUtils;
import com.github.kahlkn.artoria.util.MapUtils;

import java.io.*;
import java.util.*;

import static com.github.kahlkn.artoria.util.Const.*;

public class CsvUtils {

    public static List<Map<String, Object>> readFileToMap(File path, String encoding) throws IOException {
        Reader reader = null;
        try {
            FileInputStream in = new FileInputStream(path);
            reader = new InputStreamReader(in, encoding);
            return CsvUtils.readToMap(reader, null);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static void writeToFileByMap(List<Map<String, Object>> data, File path, String encoding) throws IOException {
        Writer writer = null;
        try {
            if (!path.exists() && !path.createNewFile()) {
                throw new IOException("Create file \"" + path.toString() + "\" failure. ");
            }
            FileOutputStream out = new FileOutputStream(path);
            writer = new OutputStreamWriter(out, encoding);
            CsvUtils.write(data, null, writer);
        }
        finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public static List<List<String>> readFile(File path) throws IOException {
        return CsvUtils.readFile(path, DEFAULT_CHARSET_NAME);
    }

    public static void writeToFile(List<List<String>> data, File path) throws IOException {
        CsvUtils.writeToFile(data, path, DEFAULT_CHARSET_NAME);
    }

    public static List<List<String>> readFile(File path, String encoding) throws IOException {
        Reader reader = null;
        try {
            FileInputStream in = new FileInputStream(path);
            reader = new InputStreamReader(in, encoding);
            return CsvUtils.read(reader);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static void writeToFile(List<List<String>> data, File path, String encoding) throws IOException {
        Writer writer = null;
        try {
            if (!path.exists() && !path.createNewFile()) {
                throw new IOException("Create file \"" + path.toString() + "\" failure. ");
            }
            FileOutputStream out = new FileOutputStream(path);
            writer = new OutputStreamWriter(out, encoding);
            CsvUtils.write(data, writer);
        }
        finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public static List<List<String>> readString(String csvString) throws IOException {
        StringReader reader = new StringReader(csvString);
        return CsvUtils.read(reader);
    }

    public static String writeToString(List<List<String>> data) throws IOException {
        StringBuilderWriter writer = new StringBuilderWriter();
        CsvUtils.write(data, writer);
        return writer.toString();
    }

    public static List<Map<String, Object>> readToMap(Reader reader, Map<String, String> table) throws IOException {
        if (reader == null) { return null; }
        BufferedReader bReader = reader instanceof BufferedReader
                ? (BufferedReader) reader : new BufferedReader(reader);
        List<String> title = new ArrayList<String>();
        boolean isTitle = true;
        boolean hasTable = MapUtils.isNotEmpty(table);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (String line; (line = bReader.readLine()) != null;) {
            String[] split = line.split(COMMA);
            List<String> list = Arrays.asList(split);
            if (isTitle) {
                for (String s : list) {
                    String tmp;
                    title.add(hasTable ? (tmp = table.get(s)) != null ? tmp : s : s);
                }
                if (CollectionUtils.isEmpty(title)) {
                    throw new IOException("Can not read title, title is first line. ");
                }
                isTitle = false;
            }
            else {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                int lSize = list.size();
                for (int i = 0, size = title.size(); i < size; i++) {
                    String val = i >= lSize ? EMPTY_STRING : list.get(i);
                    map.put(title.get(i), val);
                }
                result.add(map);
            }
        }
        return result;
    }

    public static void write(List<Map<String, Object>> data, Map<String, String> table, Writer writer) throws IOException {
        if (CollectionUtils.isEmpty(data) || writer == null) {
            return;
        }
        boolean hasTable = MapUtils.isNotEmpty(table);
        boolean isFirst = true;
        BufferedWriter bWriter = writer instanceof BufferedWriter
                ? (BufferedWriter) writer : new BufferedWriter(writer);
        String tmp;
        StringBuilder builder = new StringBuilder();
        for (Map<String, Object> map : data) {
            builder.setLength(0);
            if (isFirst) {
                for (String key : map.keySet()) {
                    tmp = hasTable ? (tmp = table.get(key)) != null ? tmp : key : key;
                    builder.append(tmp).append(COMMA);
                }
                builder.append(ENDL);
                isFirst = false;
            }
            for (Object val : map.values()) {
                builder.append(val).append(COMMA);
            }
            bWriter.write(builder.toString());
            bWriter.newLine();
        }
        bWriter.flush();
    }

    public static List<List<String>> read(Reader reader) throws IOException {
        if (reader == null) { return null; }
        BufferedReader bReader = reader instanceof BufferedReader
                ? (BufferedReader) reader : new BufferedReader(reader);
        List<List<String>> result = new ArrayList<List<String>>();
        for (String line; (line = bReader.readLine()) != null;) {
            String[] split = line.split(COMMA);
            result.add(Arrays.asList(split));
        }
        return result;
    }

    public static void write(List<List<String>> data, Writer writer) throws IOException {
        if (CollectionUtils.isEmpty(data) || writer == null) {
            return;
        }
        BufferedWriter bWriter = writer instanceof BufferedWriter
                ? (BufferedWriter) writer : new BufferedWriter(writer);
        StringBuilder builder = new StringBuilder();
        for (List<String> list : data) {
            builder.setLength(0);
            if (CollectionUtils.isEmpty(list)) {
                builder.append(COMMA);
            }
            else {
                for (String s : list) {
                    builder.append(s).append(COMMA);
                }
            }
            bWriter.write(builder.toString());
            bWriter.newLine();
            bWriter.flush();
        }
    }

}
