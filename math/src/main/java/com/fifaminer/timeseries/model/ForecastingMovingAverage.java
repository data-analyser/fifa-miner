package com.fifaminer.timeseries.model;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.math.NumberUtils.*;

public class ForecastingMovingAverage {

    private final MovingAverage movingAverage;

    public ForecastingMovingAverage(MovingAverage movingAverage) {
        this.movingAverage = movingAverage;
    }

    public Double forecast() {
        double[] timeSeries = movingAverage.getTimeSeries();
        double[] evaluatedMovingAverage = movingAverage.evaluate();
        int window = movingAverage.getWindow();

        if (shouldFindAverage(timeSeries, window)) {
            return getLast(timeSeries);
        }
        return getLast(evaluatedMovingAverage) + ((DOUBLE_ONE / window) * reduceLastByWindow(window, timeSeries));
    }

    private boolean shouldFindAverage(double[] timeSeries, int window) {
        return timeSeries.length - window == INTEGER_ONE;
    }

    private Double getLast(double[] data) {
        return data[data.length - INTEGER_ONE];
    }

    private Double reduceLastByWindow(int window, double[] timeSeries) {
        return stream(timeSeries)
                .skip(timeSeries.length - window + INTEGER_ONE)
                .reduce(0, (x, y) -> y - x);
    }
}
