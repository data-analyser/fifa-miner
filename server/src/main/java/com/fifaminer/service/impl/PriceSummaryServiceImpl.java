package com.fifaminer.service.impl;

import com.fifaminer.model.PriceSummary;
import com.fifaminer.service.PriceSummaryService;
import com.fifaminer.util.NumbersHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
import java.util.List;

import static java.util.stream.Collectors.summarizingInt;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class PriceSummaryServiceImpl implements PriceSummaryService {

    private final NumbersHelper numbersHelper;

    @Autowired
    public PriceSummaryServiceImpl(NumbersHelper numbersHelper) {
        this.numbersHelper = numbersHelper;
    }

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
                getAverage(statistics.getAverage(), prices),
                statistics.getMax()
        );
    }

    private IntSummaryStatistics calculateSummaryStatistics(List<Integer> prices) {
        return prices.stream()
                .collect(summarizingInt(price -> price));
    }

    private Integer getAverage(Double averagePrice, List<Integer> prices) {
        return numbersHelper.findClosest(averagePrice.intValue(), prices);
    }
}
