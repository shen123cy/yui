package com.github.kahlkn.yui.test.support;

import com.github.kahlkn.artoria.exception.ExceptionUtils;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.artoria.util.CollectionUtils;
import com.github.kahlkn.artoria.util.NumberUtils;
import com.github.kahlkn.yui.test.TestCode;
import com.github.kahlkn.yui.test.TestResult;
import com.github.kahlkn.yui.test.TestTask;

import java.util.ArrayList;
import java.util.List;

import static com.github.kahlkn.artoria.util.Const.EMPTY_STRING;
import static com.github.kahlkn.artoria.util.Const.ENDL;

/**
 * Base performance.
 * @author Kahle
 */
abstract class BasePerformance implements TestTask {
    private boolean status = false;
    private String name;
    private String description;
    private TestCode testCode;
    private TestResult result;

    private Integer groupTotal = 16;
    private Integer eachGroupTotal = 16;
    private Integer dataCalcTotal = 4096;
    private boolean showRawData = false;
    private List<List<Long>> rawData;
    private Throwable throwable;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public TestCode getTestCode() {
        return testCode;
    }

    @Override
    public void setTestCode(TestCode testCode) {
        Assert.notNull(testCode, "Parameter \"testCode\" must not null. ");
        this.testCode = testCode;
    }

    @Override
    public TestResult getResult() {
        Assert.notNull(rawData, "Please invoke \"execute\" first. ");
        if (result != null) { return result; }
        result = new TestResult();
        result.setStatus(status);
        result.setName(name);
        result.setDescription(description);

        StringBuilder builder = new StringBuilder();
        builder.append(">>>>>>>>").append(ENDL);

        // If has throwable.
        if (CollectionUtils.isEmpty(rawData) || throwable != null) {
            builder.append(throwable != null
                    ? ExceptionUtils.toString(throwable) : "Raw data is empty. ");
            builder.append("<<<<<<<<").append(ENDL);
            result.setReport(builder.toString());
            return result;
        }

        // Calc raw data.
        List<Double> groupAverage = averageList(rawData);
        Double average = average(groupAverage);
        List<Double> variance = varianceList(rawData, groupAverage);
        List<Double> stddeviation = stddeviationList(variance);
        Double varianceAvg = average(variance);
        Double stddeviationAvg = average(stddeviation);

        // Show groupTotal, eachGroupTotal and dataCalcTotal.
        if (groupTotal != null || eachGroupTotal != null || dataCalcTotal != null) {
            builder.append(groupTotal != null ? "-- Group total: " + groupTotal + ENDL : EMPTY_STRING);
            builder.append(eachGroupTotal != null ? "-- Each group total: " + eachGroupTotal + ENDL : EMPTY_STRING);
            builder.append(dataCalcTotal != null ? "-- Data calc total: " + dataCalcTotal + ENDL : EMPTY_STRING);
        }

        // Show raw data.
        if (showRawData) {
            builder.append("Raw data: ").append(ENDL);
            for (List<Long> list : rawData) {
                builder.append(list).append(ENDL);
            }
        }

        // Show other calc data.
        builder.append("Group average: ")
                .append(groupAverage)
                .append(ENDL);
        builder.append("Average: ")
                .append(average)
                .append(ENDL);
        builder.append("Variance: ")
                .append(variance)
                .append(ENDL);
        builder.append("Variance average: ")
                .append(varianceAvg)
                .append(ENDL);
        builder.append("Standard deviation: ")
                .append(stddeviation)
                .append(ENDL);
        builder.append("Stddeviation average: ")
                .append(stddeviationAvg)
                .append(ENDL);
        builder.append("<<<<<<<<").append(ENDL);

        result.setReport(builder.toString());
        return result;
    }

    @Override
    public void execute() {
        rawData = new ArrayList<List<Long>>();
        try {
            this.execute(rawData, groupTotal, eachGroupTotal, dataCalcTotal, testCode);
            status = true;
        }
        catch (Throwable t) {
            status = false;
            throwable = t;
        }
        result = null;
    }

    protected abstract void execute(List<List<Long>> rawData, Integer groupTotal
            , Integer eachGroupTotal, final Integer dataCalcTotal, final TestCode testCode) throws Exception;

    public Integer getGroupTotal() {
        return groupTotal;
    }

    public void setGroupTotal(Integer groupTotal) {
        Assert.state(groupTotal != null && groupTotal > 0
                , "Parameter \"groupTotal\" must greater than 0. ");
        this.groupTotal = groupTotal;
    }

    public Integer getEachGroupTotal() {
        return eachGroupTotal;
    }

    public void setEachGroupTotal(Integer eachGroupTotal) {
        Assert.state(eachGroupTotal != null && eachGroupTotal > 0 && eachGroupTotal < 2000
                , "Parameter \"eachGroupTotal\" must greater than 0 and less than 1000. ");
        this.eachGroupTotal = eachGroupTotal;
    }

    public Integer getDataCalcTotal() {
        return dataCalcTotal;
    }

    public void setDataCalcTotal(Integer dataCalcTotal) {
        Assert.state(dataCalcTotal != null && dataCalcTotal > 0
                , "Parameter \"dataCalcTotal\" must greater than 0. ");
        this.dataCalcTotal = dataCalcTotal;
    }

    public boolean getShowRawData() {
        return showRawData;
    }

    public void setShowRawData(boolean showRawData) {
        this.showRawData = showRawData;
    }

    public List<List<Long>> getRawData() {
        return rawData;
    }

    public void setRawData(List<List<Long>> rawData) {
        this.rawData = rawData;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    private static Double average(List data) {
        Assert.notEmpty(data, "Parameter \"data\" must not empty. ");
        double sum = 0d;
        for (Object number : data) {
            sum += ((Number) number).doubleValue();
        }
        return NumberUtils.round(sum / data.size()).doubleValue();
    }

    private static List<Double> averageList(List data) {
        Assert.notEmpty(data, "Parameter \"data\" must not empty. ");
        List<Double> result = new ArrayList<Double>();
        for (Object numbers : data) {
            Double average = average((List) numbers);
            result.add(average);
        }
        return result;
    }

    private static Double variance(List data, Number average) {
        Assert.notEmpty(data, "Parameter \"data\" must not empty. ");
        Assert.notNull(average, "Parameter \"average\" must not null. ");
        double sum = 0d;
        double avg = average.doubleValue();
        double num;
        for (Object number : data) {
            num = ((Number) number).doubleValue();
            sum += Math.pow((num - avg), 2);
        }
        return NumberUtils.round(sum / data.size()).doubleValue();
    }

    private static Double stddeviation(Double variance) {
        Assert.notNull(variance, "Parameter \"variance\" must not null. ");
        return NumberUtils.round(Math.sqrt(variance)).doubleValue();
    }

    private static List<Double> varianceList(List data, List averages) {
        Assert.notEmpty(data, "Parameter \"data\" must not empty. ");
        Assert.notEmpty(averages, "Parameter \"averages\" must not empty. ");
        int size = data.size();
        List<Double> result = new ArrayList<Double>();
        for (int i = 0; i < size; i++) {
            List list = (List) data.get(i);
            Number number = (Number) averages.get(i);
            Double variance = variance(list, number);
            result.add(variance);
        }
        return result;
    }

    private static List<Double> stddeviationList(List variances) {
        Assert.notEmpty(variances, "Parameter \"variances\" must not empty. ");
        List<Double> result = new ArrayList<Double>();
        Double num;
        for (Object variance : variances) {
            num = ((Number) variance).doubleValue();
            Double d = stddeviation(num);
            result.add(d);
        }
        return result;
    }

}
