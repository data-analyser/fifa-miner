package com.fifaminer.statistics.impl;

import com.fifaminer.statistics.StatisticsService;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final double MEDIAN_PERCENTILE = 50;

    @Override
    public Integer median(List<Integer> values) {
        values.sort(Integer::compare);
        return calculateMedan(values);
    }

    private Integer calculateMedan(List<Integer> sortedValues) {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        sortedValues.forEach(descriptiveStatistics::addValue);
        return Double.valueOf(descriptiveStatistics.getPercentile(MEDIAN_PERCENTILE))
                .intValue();
    }
}
