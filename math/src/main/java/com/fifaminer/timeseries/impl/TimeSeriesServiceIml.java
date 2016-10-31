package com.fifaminer.timeseries.impl;

import com.cloudera.sparkts.models.ARIMA;
import com.fifaminer.timeseries.TimeSeriesService;
import com.google.common.collect.Iterables;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.apache.commons.lang3.ArrayUtils.toPrimitive;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

@Service
public class TimeSeriesServiceIml implements TimeSeriesService {

    @Override
    public Double forecastArima(List<Double> timeSeries) {
        Vector dataVector = Vectors.dense(toPrimitives(timeSeries));
        Vector forecast = ARIMA.fitModel(1, 1, 0, dataVector, true, "css-cgd", null)
                .forecast(dataVector, INTEGER_ONE);
        return Iterables.getLast(toIterable(forecast.toArray()));
    }

    private List<Double> toIterable(double[] vector) {
        return stream(toObject(vector))
                .collect(toList());
    }

    private double[] toPrimitives(List<Double> timeSeries) {
        return toPrimitive(timeSeries.stream().toArray(Double[]::new));
    }
}
