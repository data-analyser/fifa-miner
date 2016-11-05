package com.fifaminer.service.transaction.impl;

import com.fifaminer.entity.Transaction.TransactionRecord;
import com.fifaminer.entity.pojo.TransactionType;
import com.fifaminer.repository.TransactionAnalyseRepository;
import com.fifaminer.service.common.ClockService;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import com.fifaminer.service.transaction.TransactionService;
import com.fifaminer.entity.TransactionStatistics;
import com.fifaminer.statistics.StatisticsService;
import com.fifaminer.util.Percentage;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static com.fifaminer.entity.pojo.TransactionType.*;
import static java.lang.Long.compare;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.*;

@Service
public class TransactionAnalysingServiceImpl implements TransactionAnalysingService {

    private final TransactionService transactionService;
    private final StatisticsService statisticsService;
    private final TransactionAnalyseRepository transactionAnalyseRepository;
    private final ClockService clockService;

    @Autowired
    public TransactionAnalysingServiceImpl(TransactionService transactionService,
                                           StatisticsService statisticsService,
                                           TransactionAnalyseRepository transactionAnalyseRepository,
                                           ClockService clockService) {
        this.transactionService = transactionService;
        this.statisticsService = statisticsService;
        this.transactionAnalyseRepository = transactionAnalyseRepository;
        this.clockService = clockService;
    }

    @Override
    public TransactionStatistics analyse(Long playerId) {
        List<TransactionRecord> records = transactionService.findByPlayerId(playerId)
                .getRecords();

        Long relists = getTransactionCountByType(records, RELIST);
        return new TransactionStatistics(
                playerId,
                clockService.now(),
                getTransactionCountByType(records, BOUGHT_CARD, BOUGHT_BY_ROBOT),
                getTransactionCountByType(records, PLACED_TO_MARKET),
                getTransactionCountByType(records, SELL_CARD),
                relists,
                Long.valueOf(records.size()),
                Percentage.ofTotal(records.size(), relists.intValue()),
                getMedianSellTime(records),
                getMedianSellPrice(records)
        );
    }

    @Override
    public void saveStatistics(TransactionStatistics transactionStatistics) {
        transactionAnalyseRepository.save(transactionStatistics);
    }

    private Long getTransactionCountByType(List<TransactionRecord> records,
                                           TransactionType... transactionTypes) {
        return records.stream()
                .filter(record -> ArrayUtils.contains(transactionTypes, record.getOperation()))
                .count();
    }

    private Integer getMedianSellPrice(List<TransactionRecord> records) {
        List<Integer> sellPrices = records.stream()
                .filter(record -> record.getOperation().equals(SELL_CARD))
                .map(TransactionRecord::getCoins)
                .collect(toList());

        return statisticsService.median(sellPrices);
    }

    private Integer getMedianSellTime(List<TransactionRecord> records) {
        List<Long> sellTimes = records.stream()
                .sorted((current, next) -> compare(current.getTimestamp(), next.getTimestamp()))
                .filter(record -> record.getOperation().equals(SELL_CARD))
                .map(TransactionRecord::getTimestamp)
                .collect(toList());

        return sellTimes.size() == INTEGER_ONE ? INTEGER_ZERO : calculateDeltasMedian(sellTimes);
    }

    private Integer calculateDeltasMedian(List<Long> sellTimes) {
        List<Integer> deltas = IntStream.range(INTEGER_ZERO, sellTimes.size() - INTEGER_ONE)
                .mapToLong(i -> sellTimes.get(i + INTEGER_ONE) - sellTimes.get(i))
                .boxed()
                .map(Long::intValue)
                .collect(toList());

        return statisticsService.median(deltas);
    }
}
