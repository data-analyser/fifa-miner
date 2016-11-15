package com.fifaminer.service.marketing.impl;

import com.fifaminer.entity.TransactionStatistics;
import com.fifaminer.service.marketing.MarketingService;
import com.fifaminer.service.marketing.strategy.PlayerSortingFactory;
import com.fifaminer.service.marketing.type.OrderingType;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import com.fifaminer.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MarketingServiceImpl implements MarketingService {

    private final TransactionAnalysingService transactionAnalysingService;
    private final TransactionService transactionService;
    private final PlayerSortingFactory sortingTypeFactory;

    private static final Integer MIN_TRANSACTIONS_COUNT_FOR_ANALYSE = 10;

    @Autowired
    public MarketingServiceImpl(TransactionAnalysingService transactionAnalysingService,
                                TransactionService transactionService,
                                PlayerSortingFactory sortingTypeFactory) {
        this.transactionAnalysingService = transactionAnalysingService;
        this.transactionService = transactionService;
        this.sortingTypeFactory = sortingTypeFactory;
    }

    @Override
    public List<Long> findPlayersByTransactionAnalyse(Long startTime,
                                                      Long endTime,
                                                      OrderingType orderingType,
                                                      Integer limit) {
        return transactionService.findRecordsWhereTimestampBetween(startTime, endTime)
                .stream()
                .filter(transaction -> transaction.getRecords().size() >= MIN_TRANSACTIONS_COUNT_FOR_ANALYSE)
                .map(transactionAnalysingService::analyseOnFly)
                .sorted(sortingTypeFactory.create(orderingType))
                .map(TransactionStatistics::getPlayerId)
                .limit(limit)
                .collect(toList());
    }
}
