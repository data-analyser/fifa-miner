package com.fifaminer.timeseries.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

import static com.fifaminer.timeseries.util.ForecastingPrecondition.isSingular;

public class ArimaPrecondition {

    private static final int MIN_ARIMA_LENGTH = 4;

    public static boolean canForecast(List<Double> timeSeries) {
        return !(timeSeries.size() < MIN_ARIMA_LENGTH || isSingular(toPrimitive(timeSeries)));
    }

    private static double[] toPrimitive(List<Double> timeSeries) {
        return ArrayUtils.toPrimitive(
                timeSeries.stream().toArray(Double[]::new)
        );
    }
}
