package com.fifaminer.service.price.policy.impl;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SellPriceDefinitionPolicy {

    private final TaxService taxService;
    private final PriceBoundService priceBoundService;

    @Autowired
    public SellPriceDefinitionPolicy(TaxService taxService,
                                     PriceBoundService priceBoundService) {
        this.taxService = taxService;
        this.priceBoundService = priceBoundService;
    }

    public Integer define(Double forecastedMedian) {
        return priceBoundService.arrangeToBound(taxService.addTax(forecastedMedian.intValue()));
    }
}
