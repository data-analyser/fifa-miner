package com.fifaminer.service.marketing.impl;

import com.fifaminer.service.marketing.MarketingService;
import com.fifaminer.service.marketing.strategy.PlayerSortingFactory;
import com.fifaminer.service.marketing.type.OrderingType;
import com.fifaminer.service.price.PriceService;
import com.fifaminer.service.price.model.PlayerPrice;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import com.fifaminer.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class MarketingServiceImpl implements MarketingService {

    private final TransactionAnalysingService transactionAnalysingService;
    private final TransactionService transactionService;
    private final PlayerSortingFactory sortingTypeFactory;
    private final PriceService priceService;

    private static final Integer MIN_TRANSACTIONS_COUNT_FOR_ANALYSE = 10;

    @Autowired
    public MarketingServiceImpl(TransactionAnalysingService transactionAnalysingService,
                                TransactionService transactionService,
                                PlayerSortingFactory sortingTypeFactory,
                                PriceService priceService) {
        this.transactionAnalysingService = transactionAnalysingService;
        this.transactionService = transactionService;
        this.sortingTypeFactory = sortingTypeFactory;
        this.priceService = priceService;
    }

    @Override
    public List<PlayerPrice> findPlayersByTransactionAnalyse(Long startTime,
                                                             Long endTime,
                                                             OrderingType orderingType,
                                                             Integer limit) {
        return transactionService.findRecordsWhereTimestampBetween(startTime, endTime)
                .stream()
                .filter(transaction -> transaction.getRecords().size() >= MIN_TRANSACTIONS_COUNT_FOR_ANALYSE)
                .map(transactionAnalysingService::analyseOnFly)
                .sorted(sortingTypeFactory.create(orderingType))
                .map(statistics -> priceService.getPlayerPriceInfo(statistics.getPlayerId()))
                .filter(hasAcceptableForMarketingPrices())
                .limit(limit)
                .collect(toList());
    }

    private Predicate<PlayerPrice> hasAcceptableForMarketingPrices() {
        return playerPrice -> (!playerPrice.getBuyPrice().equals(INTEGER_ZERO) && !playerPrice.getSellPrice().equals(INTEGER_ZERO))
                && (playerPrice.getBuyPrice() < playerPrice.getSellPrice());
    }
}
