package com.fifaminer.service.impl;

import com.fifaminer.model.PriceSummary;
import com.fifaminer.service.PriceSummaryService;
import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
import java.util.List;

import static java.lang.Double.valueOf;
import static java.util.stream.Collectors.summarizingInt;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PriceSummaryServiceImpl implements PriceSummaryService {

    @Override
    public PriceSummary calculatePriceSummary(Long timestamp, List<Integer> prices) {
        if (isEmpty(prices)) {
            return new PriceSummary(timestamp, INTEGER_ZERO, INTEGER_ZERO, INTEGER_ZERO);
        }
        return getPriceSummary(timestamp, prices);
    }

    private PriceSummary getPriceSummary(Long timestamp, List<Integer> prices) {
        IntSummaryStatistics statistics = calculateSummaryStatistics(prices);
        return new PriceSummary(
                timestamp,
                statistics.getMin(),
                valueOf(statistics.getAverage()).intValue(),
                statistics.getMax()
        );
    }

    private IntSummaryStatistics calculateSummaryStatistics(List<Integer> prices) {
        return prices.stream()
                .collect(summarizingInt(price -> price));
    }
}
