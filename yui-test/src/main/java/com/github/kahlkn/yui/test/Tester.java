package com.github.kahlkn.yui.test;

import com.github.kahlkn.artoria.logging.Logger;
import com.github.kahlkn.artoria.logging.LoggerFactory;
import com.github.kahlkn.artoria.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.kahlkn.artoria.util.Const.EMPTY_STRING;
import static com.github.kahlkn.artoria.util.Const.ENDL;

/**
 * Tested code executor.
 * @author Kahle
 */
public class Tester {
    private static Logger log = LoggerFactory.getLogger(Tester.class);
    private String title;
    private String description;
    private final List<TestTask> tasks = new ArrayList<TestTask>();

    public String getTitle() {
        return title;
    }

    public Tester setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Tester setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<TestTask> getTasks() {
        return tasks;
    }

    public Tester addTasks(TestTask... tasks) {
        this.tasks.addAll(Arrays.asList(tasks));
        return this;
    }

    public Tester execute() {
        for (int i = 0, size = tasks.size(); i < size; i++) {
            TestTask task = tasks.get(i);
            String name = task.getName();
            name = StringUtils.isNotBlank(name) ? name : "Task" + i;
            log.info("Start test task \"" + name + "\". ");
            long start = System.currentTimeMillis();
            task.execute();
            long end = System.currentTimeMillis();
            log.info("End test task \"" + name + "\" and cost " + (end - start) + "ms. ");
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(StringUtils.isNotBlank(title) ? title + ENDL : EMPTY_STRING);
        builder.append(StringUtils.isNotBlank(description) ? description + ENDL : EMPTY_STRING);
        builder.append(ENDL);
        for (int i = 0, size = tasks.size(); i < size; i++) {
            TestTask task = tasks.get(i);
            String name = task.getName();
            builder.append("Name: ").append(StringUtils.isNotBlank(name) ? name : "Task" + i).append(ENDL);
            boolean status = task.getStatus();
            builder.append("Status: ").append(status ? "Success" : "Failure").append(ENDL);
            String description = task.getDescription();
            builder.append("Description: ").append(StringUtils.isNotBlank(description) ? description + ENDL : ENDL);
            String result = task.getResult();
            builder.append("Result: ").append(ENDL)
                    .append(StringUtils.isNotBlank(result) ? result + ENDL : EMPTY_STRING);
        }
        return builder.append(ENDL).toString();
    }

}
