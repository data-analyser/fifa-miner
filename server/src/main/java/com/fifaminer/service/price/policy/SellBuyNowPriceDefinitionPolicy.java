package com.fifaminer.service.price.policy;

import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.SellBuyNowPriceStrategyService;
import com.fifaminer.service.price.TaxService;
import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;
import com.fifaminer.service.price.model.PriceStatistics;
import com.fifaminer.service.price.type.BoundSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SellBuyNowPriceDefinitionPolicy {

    private final TaxService taxService;
    private final PriceBoundService priceBoundService;
    private final SellBuyNowPriceStrategyService sellBuyNowPriceStrategyService;

    @Autowired
    public SellBuyNowPriceDefinitionPolicy(TaxService taxService,
                                           PriceBoundService priceBoundService,
                                           SellBuyNowPriceStrategyService sellBuyNowPriceStrategyService) {
        this.taxService = taxService;
        this.priceBoundService = priceBoundService;
        this.sellBuyNowPriceStrategyService = sellBuyNowPriceStrategyService;
    }

    public Integer define(Double forecastedMin,
                          Double forecastedMedian,
                          List<PriceStatistics> priceStatistics) {
        SellBuyNowPriceDefinitionContext sellBuyNowPriceDefinitionContext = new SellBuyNowPriceDefinitionContext(
                forecastedMin.intValue(), forecastedMedian.intValue(), priceStatistics);

        Integer sellBuyNowPriceByStrategy = sellBuyNowPriceStrategyService.findActiveSellBuyNowStrategy()
                .calculate(sellBuyNowPriceDefinitionContext);

        return priceBoundService.arrangeToBound(
                taxService.addTax(sellBuyNowPriceByStrategy), BoundSelection.HIGHER
        );
    }
}
