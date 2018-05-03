package com.github.kahlkn.yui.test;

import com.github.kahlkn.artoria.exception.UncheckedException;
import com.github.kahlkn.artoria.util.ArrayUtils;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.artoria.util.CollectionUtils;
import com.github.kahlkn.artoria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.kahlkn.artoria.util.Const.*;

/**
 * Test tools.
 * @author Kahle
 */
public class TestUtils {
    private static Logger log = LoggerFactory.getLogger(TestUtils.class);

    public static void addTask(List<TestTask> tasks, Class<? extends TestTask> clazz, String name, TestCode testCode) {
        try {
            TestTask testTask = clazz.newInstance();
            testTask.setName(name);
            testTask.setTestCode(testCode);
            tasks.add(testTask);
        }
        catch (Exception e) {
            throw new UncheckedException(e);
        }
    }

    public static List<TestResult> execute(TestTask... tasks) {
        return TestUtils.execute(ArrayUtils.isNotEmpty(tasks) ? Arrays.asList(tasks) : null);
    }

    public static List<TestResult> execute(List<TestTask> tasks) {
        List<TestResult> results = new ArrayList<TestResult>();
        if (CollectionUtils.isEmpty(tasks)) { return results; }
        for (int i = 0, size = tasks.size(); i < size; i++) {
            TestTask task = tasks.get(i);
            String name = task.getName();
            name = StringUtils.isNotBlank(name) ? name : "Task" + i;
            log.info("Start test task \"{}\". ", name);
            long start = System.currentTimeMillis();
            task.execute();
            long end = System.currentTimeMillis();
            log.info("End test task \"{}\" and cost {}ms. ", name, end - start);
            results.add(task.getResult());
        }
        return results;
    }

    public static String toString(TestResult... results) {
        if (ArrayUtils.isEmpty(results)) { return null; }
        return TestUtils.toString(Arrays.asList(results));
    }

    public static String toString(List<TestResult> results) {
        if (CollectionUtils.isEmpty(results)) { return null; }
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = results.size(); i < size; i++) {
            TestResult result = results.get(i);
            boolean status = result.getStatus();
            String name = result.getName();
            String desc = result.getDescription();
            String report = result.getReport();
            builder.append(status ? "Success" : "Failure").append(": ");
            builder.append(StringUtils.isNotBlank(name) ? name : "Task" + i).append(ENDL);
            if (StringUtils.isNotBlank(desc)) {
                builder.append("Description: ").append(desc).append(ENDL);
            }
            builder.append("Report: ").append(ENDL);
            builder.append(StringUtils.isNotBlank(report) ? report : EMPTY_STRING).append(ENDL);
            builder.append(ENDL);
        }
        return builder.toString();
    }

    public static void toFile(File file, String encoding, TestResult... results) throws IOException {
        Assert.notEmpty(results, "Parameter \"results\" must not empty. ");
        TestUtils.toFile(file, encoding, Arrays.asList(results));
    }

    public static void toFile(File file, String encoding, List<TestResult> results) throws IOException {
        Assert.notNull(file, "Parameter \"file\" must not null. ");
        Assert.notEmpty(results, "Parameter \"results\" must not empty. ");
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("File \"" + file + "\" not exist, And create failure. ");
        }
        String result = TestUtils.toString(results);
        if (StringUtils.isBlank(result)) { return; }
        if (StringUtils.isBlank(encoding)) { encoding = DEFAULT_CHARSET_NAME; }
        OutputStream out = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, encoding));
        writer.write(result);
    }

}
