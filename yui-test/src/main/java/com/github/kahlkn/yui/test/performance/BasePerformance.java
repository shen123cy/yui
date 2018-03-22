package com.github.kahlkn.sagiri.test.performance;

import com.github.kahlkn.artoria.exception.ExceptionUtils;
import com.github.kahlkn.artoria.util.Assert;
import com.github.kahlkn.artoria.util.CollectionUtils;
import com.github.kahlkn.artoria.util.NumberUtils;
import com.github.kahlkn.sagiri.test.TestTask;
import com.github.kahlkn.sagiri.test.Tested;

import java.util.ArrayList;
import java.util.List;

import static com.github.kahlkn.artoria.util.Const.EMPTY_STRING;
import static com.github.kahlkn.artoria.util.Const.ENDL;

/**
 * Base performance.
 * @author Kahle
 */
public abstract class BasePerformance implements TestTask {
    protected boolean status = false;
    protected String name;
    protected String description;
    protected Tested tested;

    protected Integer groupTotal;
    protected Integer eachGroupTotal;
    protected Integer dataCalcTotal;
    protected boolean showRawData = false;
    protected List<List<Long>> rawData;
    protected Throwable throwable;

    public BasePerformance(Integer groupTotal, Integer eachGroupTotal, Integer dataCalcTotal, Tested tested) {
        Assert.state(groupTotal != null && groupTotal > 0
                , "Parameter \"groupTotal\" must greater than 0. ");
        Assert.state(eachGroupTotal != null && eachGroupTotal > 0 && eachGroupTotal < 2000
                , "Parameter \"eachGroupTotal\" must greater than 0 and less than 1000. ");
        Assert.state(dataCalcTotal != null && dataCalcTotal > 0
                , "Parameter \"dataCalcTotal\" must greater than 0. ");
        Assert.notNull(tested, "Parameter \"tested\" must not null. ");
        this.groupTotal = groupTotal;
        this.eachGroupTotal = eachGroupTotal;
        this.dataCalcTotal = dataCalcTotal;
        this.tested = tested;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGroupTotal() {
        return groupTotal;
    }

    public Integer getEachGroupTotal() {
        return eachGroupTotal;
    }

    public Integer getDataCalcTotal() {
        return dataCalcTotal;
    }

    public boolean getShowRawData() {
        return showRawData;
    }

    public void setShowRawData(boolean showRawData) {
        this.showRawData = showRawData;
    }

    @Override
    public String getResult() {
        Assert.notNull(rawData, "Please invoke \"execute\" first. ");

        StringBuilder builder = new StringBuilder();
        builder.append(">>>>>>>>").append(ENDL);

        // If has throwable.
        if (CollectionUtils.isEmpty(rawData) || throwable != null) {
            builder.append(throwable != null
                    ? ExceptionUtils.toString(throwable) : "Raw data is empty. ");
            builder.append("<<<<<<<<").append(ENDL);
            return builder.toString();
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

        return builder.toString();
    }

    protected static Double average(List data) {
        Assert.notEmpty(data, "Parameter \"data\" must not empty. ");
        double sum = 0d;
        for (Object number : data) {
            sum += ((Number) number).doubleValue();
        }
        return NumberUtils.round(sum / data.size());
    }

    protected static List<Double> averageList(List data) {
        Assert.notEmpty(data, "Parameter \"data\" must not empty. ");
        List<Double> result = new ArrayList<Double>();
        for (Object numbers : data) {
            Double average = average((List) numbers);
            result.add(average);
        }
        return result;
    }

    protected static Double variance(List data, Number average) {
        Assert.notEmpty(data, "Parameter \"data\" must not empty. ");
        Assert.notNull(average, "Parameter \"average\" must not null. ");
        double sum = 0d;
        double avg = average.doubleValue();
        double num;
        for (Object number : data) {
            num = ((Number) number).doubleValue();
            sum += Math.pow((num - avg), 2);
        }
        return NumberUtils.round(sum / data.size());
    }

    protected static Double stddeviation(Double variance) {
        Assert.notNull(variance, "Parameter \"variance\" must not null. ");
        return NumberUtils.round(Math.sqrt(variance));
    }

    protected static List<Double> varianceList(List data, List averages) {
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

    protected static List<Double> stddeviationList(List variances) {
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
