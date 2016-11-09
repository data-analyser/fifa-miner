package com.fifaminer.timeseries.util;

import java.util.stream.DoubleStream;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

public class ForecastingPrecondition {

    public static boolean isSingular(double[] timeSeries) {
        return DoubleStream.of(timeSeries)
                .distinct()
                .count() == INTEGER_ONE;
    }
}
