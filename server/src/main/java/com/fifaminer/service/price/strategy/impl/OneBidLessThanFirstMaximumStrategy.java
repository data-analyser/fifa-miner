package com.fifaminer.service.price.strategy.impl;

import com.fifaminer.service.common.ClockService;
import com.fifaminer.service.price.PriceBoundService;
import com.fifaminer.service.price.PriceStatisticsService;
import com.fifaminer.service.price.strategy.SellBuyNowPriceStrategy;
import com.fifaminer.service.price.model.DeviationPriceDistribution;
import com.fifaminer.service.price.model.PriceStatistics;
import com.fifaminer.service.price.model.PricesDistribution;
import com.fifaminer.service.price.model.SellBuyNowPriceDefinitionContext;
import com.fifaminer.service.price.type.BoundSelection;
import com.google.common.collect.ComparisonChain;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.google.common.collect.Ordering.natural;
import static java.util.Collections.min;
import static java.util.stream.Collectors.toList;

@Component
public class OneBidLessThanFirstMaximumStrategy implements SellBuyNowPriceStrategy {

    private final PriceBoundService priceBoundService;
    private final PriceStatisticsService priceStatisticsService;
    private final ClockService clockService;

    private static final int BID_STEPS = 1;
    private static final int PROCESSABLE_DEVIATIONS = 2;
    private static final int MIN_PRICE_DISTRIBUTION_SIZE = 2;

    public OneBidLessThanFirstMaximumStrategy(PriceBoundService priceBoundService,
                                              PriceStatisticsService priceStatisticsService,
                                              ClockService clockService) {
        this.priceBoundService = priceBoundService;
        this.priceStatisticsService = priceStatisticsService;
        this.clockService = clockService;
    }

    @Override
    public Integer calculate(SellBuyNowPriceDefinitionContext context) {
        List<PricesDistribution> pricesDistribution = getPricesLessThanMedianAndMoreThanMin(
                context.getPricesDistribution()
        );

        if (pricesDistribution.size() == 0) {
            return min(context.getPricesDistribution().keySet());
        }

        if (pricesDistribution.size() <= MIN_PRICE_DISTRIBUTION_SIZE)
            return getMinimalSellersPrice(pricesDistribution);

        Integer maxPriceForSelling = findPriceByDistribution(pricesDistribution);
        return priceBoundService.arrangeToSteps(maxPriceForSelling, BID_STEPS, BoundSelection.LOWER);
    }

    private Integer getMinimalSellersPrice(List<PricesDistribution> pricesDistribution) {
        PricesDistribution pricesBySellers = pricesDistribution.stream()
                .sorted(bySellers())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find correct buy now price"));

        return pricesBySellers.getPrice();
    }

    private Integer findPriceByDistribution(List<PricesDistribution> pricesDistribution) {
        List<DeviationPriceDistribution> pricesDeviation = IntStream.range(0, pricesDistribution.size() - 1)
                .mapToObj(i -> {
                    PricesDistribution current = pricesDistribution.get(i);
                    PricesDistribution next = pricesDistribution.get(i + 1);
                    return new DeviationPriceDistribution(
                            next.getSellers() - current.getSellers(),
                            new PricesDistribution(next.getPrice(), next.getSellers())
                    );
                })
                .filter(deviation -> deviation.getDeviation() > 0)
                .collect(toList());

        if (pricesDeviation.size() == 0) {
            return getMinimalSellersPrice(pricesDistribution);
        }

        PricesDistribution targetSellers = pricesDeviation.stream()
                .sorted(byDeviation().reversed())
                .limit(PROCESSABLE_DEVIATIONS)
                .map(DeviationPriceDistribution::getPricesDistribution)
                .sorted(byPrice())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find correct buy now price"));

        return targetSellers.getPrice();
    }

    private List<PricesDistribution> getPricesLessThanMedianAndMoreThanMin(Map<Integer, Integer> pricesDistribution) {
        PriceStatistics priceStatistics = priceStatisticsService.calculatePriceStatistics(
                clockService.now(), pricesDistribution
        );

        return pricesDistribution.entrySet()
                .stream()
                .filter(entry -> entry.getKey() <= priceStatistics.getMedian())
                .filter(entry -> entry.getKey() > min(pricesDistribution.keySet()))
                .map(entry -> new PricesDistribution(entry.getKey(), entry.getValue()))
                .sorted(byPrice())
                .collect(toList());
    }

    private Comparator<PricesDistribution> byPrice() {
        return (current, next) -> Integer.compare(current.getPrice(), next.getPrice());
    }

    private Comparator<PricesDistribution> bySellers() {
        return (current, next) -> ComparisonChain.start()
                .compare(current.getSellers(), next.getSellers())
                .compare(current.getPrice(), next.getPrice(), natural().reversed())
                .result();
    }

    private Comparator<DeviationPriceDistribution> byDeviation() {
        return (current, next) -> Integer.compare(current.getDeviation(), next.getDeviation());
    }
}
