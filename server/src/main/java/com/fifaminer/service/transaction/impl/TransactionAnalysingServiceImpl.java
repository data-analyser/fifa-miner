package com.fifaminer.service.transaction.impl;

import com.fifaminer.entity.Transaction;
import com.fifaminer.entity.Transaction.TransactionRecord;
import com.fifaminer.entity.pojo.TransactionStatisticsData;
import com.fifaminer.entity.pojo.TransactionType;
import com.fifaminer.repository.TransactionAnalyseRepository;
import com.fifaminer.service.common.ClockService;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import com.fifaminer.entity.TransactionStatistics;
import com.fifaminer.statistics.StatisticsService;
import com.fifaminer.util.Percentage;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static com.fifaminer.entity.pojo.TransactionType.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Long.compare;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.*;

@Service
public class TransactionAnalysingServiceImpl implements TransactionAnalysingService {

    private final TransactionAnalyseRepository transactionAnalyseRepository;
    private final StatisticsService statisticsService;
    private final ClockService clockService;

    @Autowired
    public TransactionAnalysingServiceImpl(TransactionAnalyseRepository transactionAnalyseRepository,
                                           StatisticsService statisticsService,
                                           ClockService clockService) {
        this.statisticsService = statisticsService;
        this.transactionAnalyseRepository = transactionAnalyseRepository;
        this.clockService = clockService;
    }

    @Override
    public TransactionStatistics analyse(Transaction transaction) {
        return createStatistics(transaction);
    }

    @Override
    public void saveAll(List<TransactionStatistics> transactionStatistics) {
        transactionAnalyseRepository.save(transactionStatistics);
    }

    @Override
    public List<TransactionStatistics> findAll() {
        return transactionAnalyseRepository.findAll();
    }

    private TransactionStatistics createStatistics(Transaction transaction) {
        List<TransactionRecord> records = transaction.getRecords();
        Long relists = getTransactionCountByType(records, RELIST);

        TransactionStatisticsData transactionStatisticsData = new TransactionStatisticsData(
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

        TransactionStatistics transactionStatistics = transactionAnalyseRepository.findOne(transaction.getPlayerId());

        if (isNull(transactionStatistics)) {
            return new TransactionStatistics(transaction.getPlayerId(), newArrayList(transactionStatisticsData));
        }

        transactionStatistics.getStatisticsData().add(transactionStatisticsData);
        return transactionStatistics;
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
