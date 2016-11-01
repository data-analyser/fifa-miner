package com.fifaminer.timeseries.util;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

public class ArimaPrecondition {

    private static final int MIN_ARIMA_LENGTH = 4;

    public static boolean canForecast(List<Double> timeSeries) {
        return !(timeSeries.size() < MIN_ARIMA_LENGTH || isSingular(timeSeries));
    }

    private static boolean isSingular(List<Double> timeSeries) {
        return timeSeries.stream()
                .distinct()
                .count() == INTEGER_ONE;
    }
}
