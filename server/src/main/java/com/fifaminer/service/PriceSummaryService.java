package com.fifaminer.service;

import com.fifaminer.model.PriceSummary;

import java.util.List;

public interface PriceSummaryService {

    PriceSummary calculatePriceSummary(Long timestamp, List<Integer> prices);
}
