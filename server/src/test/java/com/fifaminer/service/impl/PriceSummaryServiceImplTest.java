package com.fifaminer.service.impl;

import com.fifaminer.model.PriceSummary;
import com.fifaminer.service.PriceSummaryService;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PriceSummaryServiceImplTest {

    private final PriceSummaryService priceSummaryService = new PriceSummaryServiceImpl();

    private final Long timestamp = Long.MAX_VALUE;

    @Test
    public void calculatePriceSummaryEmptyPrices() {
        PriceSummary priceSummary = priceSummaryService.calculatePriceSummary(timestamp, emptyList());
        assertPriceSummary(priceSummary, 0, 0, 0);
    }

    @Test
    public void calculatePriceSummaryOnePrice() {
        PriceSummary priceSummary = priceSummaryService.calculatePriceSummary(timestamp, newArrayList(2));
        assertPriceSummary(priceSummary, 2, 2, 2);
    }

    @Test
    public void calculatePriceSummaryTwoPrices() {
        PriceSummary priceSummary = priceSummaryService.calculatePriceSummary(timestamp, newArrayList(1, 2));
        assertPriceSummary(priceSummary, 1, 1, 2);
    }

    @Test
    public void calculatePriceSummaryThreePrices() {
        PriceSummary priceSummary = priceSummaryService.calculatePriceSummary(timestamp, newArrayList(1, 2, 3));
        assertPriceSummary(priceSummary, 1, 2, 3);
    }

    @Test
    public void calculatePriceSummaryIntegerAverage() {
        PriceSummary priceSummary = priceSummaryService.calculatePriceSummary(timestamp, newArrayList(11, 120));
        assertPriceSummary(priceSummary, 11, 65, 120);
    }

    @Test
    public void calculatePriceSummaryUnorderedPrices() {
        PriceSummary priceSummary = priceSummaryService.calculatePriceSummary(timestamp, newArrayList(70, 50, 30));
        assertPriceSummary(priceSummary, 30, 50, 70);
    }

    private void assertPriceSummary(PriceSummary priceSummary, Integer min, Integer avg, Integer max) {
        assertThat(priceSummary.getTimestamp(), is(timestamp));
        assertThat(priceSummary.getMin(), is(min));
        assertThat(priceSummary.getAvg(), is(avg));
        assertThat(priceSummary.getMax(), is(max));
    }
}