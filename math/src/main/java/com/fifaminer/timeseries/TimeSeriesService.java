package com.fifaminer.timeseries;

import java.util.List;

public interface TimeSeriesService {

    Double forecastArima(List<Double> timeSeries);
}
