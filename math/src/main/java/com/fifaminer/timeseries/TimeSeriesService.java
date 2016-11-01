package com.fifaminer.timeseries;

import java.util.List;

public interface TimeSeriesService {

    Double forecast(List<Double> timeSeries);
}
