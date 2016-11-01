package com.fifaminer.timeseries.model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MovingAverage {

    private final double[] timeSeries;
    private final int window;

    public MovingAverage(double[] timeSeries, int window) {
        this.timeSeries = timeSeries;
        this.window = window;
    }

    public double[] getTimeSeries() {
        return timeSeries;
    }

    public int getWindow() {
        return window;
    }

    public double[] evaluate() {
        double[] sums = Arrays.copyOf(timeSeries, timeSeries.length);
        Arrays.parallelPrefix(sums, Double::sum);
        int start = window - 1;
        return IntStream.range(start, sums.length)
                .mapToDouble(i -> {
                    double prefix = i == start ? 0 : sums[i - window];
                    return (sums[i] - prefix) / window;

                })
                .toArray();
    }
}
