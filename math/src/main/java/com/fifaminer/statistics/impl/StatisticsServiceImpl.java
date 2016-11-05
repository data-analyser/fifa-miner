package com.fifaminer.statistics.impl;

import com.fifaminer.statistics.StatisticsService;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final double MEDIAN_PERCENTILE = 50;

    @Override
    public Integer median(List<Integer> values) {
        return calculateMedian(sort(values));
    }

    private List<Integer> sort(List<Integer> values) {
        List<Integer> sortedValues = newArrayList(values);
        sortedValues.sort(Integer::compare);
        return sortedValues;
    }

    private Integer calculateMedian(List<Integer> sortedValues) {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        sortedValues.forEach(descriptiveStatistics::addValue);
        return Double.valueOf(descriptiveStatistics.getPercentile(MEDIAN_PERCENTILE))
                .intValue();
    }
}
