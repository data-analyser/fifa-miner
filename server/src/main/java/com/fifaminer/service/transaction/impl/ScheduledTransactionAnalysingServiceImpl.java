package com.fifaminer.service.transaction.impl;

import com.fifaminer.entity.Transaction;
import com.fifaminer.entity.TransactionStatistics;
import com.fifaminer.entity.pojo.TransactionStatisticsData;
import com.fifaminer.service.transaction.ScheduledTransactionAnalysingService;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import com.fifaminer.service.transaction.TransactionService;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ScheduledTransactionAnalysingServiceImpl implements ScheduledTransactionAnalysingService {

    private final TransactionService transactionService;
    private final TransactionAnalysingService transactionAnalysingService;

    @Autowired
    public ScheduledTransactionAnalysingServiceImpl(TransactionService transactionService,
                                                    TransactionAnalysingService transactionAnalysingService) {
        this.transactionService = transactionService;
        this.transactionAnalysingService = transactionAnalysingService;
    }

    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    @Override
    public void analyse() {
        log.info("Start scheduled transactions analyse");
        transactionService.findAll().forEach(this::calculateForNewTransactions);
    }

    private void calculateForNewTransactions(Transaction transaction) {
        Optional<TransactionStatistics> transactionStatistics = transactionAnalysingService
                .findByPlayerId(transaction.getPlayerId());

        if (!transactionStatistics.isPresent()) {
            transactionAnalysingService.save(transactionAnalysingService.analyse(transaction));
        } else {
            recalculateIfChanged(transaction, transactionStatistics.get());
        }
    }

    private void recalculateIfChanged(Transaction transaction, TransactionStatistics transactionStatistics) {
        TransactionStatisticsData statisticsData = Iterables.getLast(transactionStatistics.getStatisticsData());
        if (hasNewTransactions(transaction, statisticsData)) {
            transactionAnalysingService.save(transactionAnalysingService.analyse(transaction));
        }
    }

    private boolean hasNewTransactions(Transaction transaction, TransactionStatisticsData statisticsData) {
        return !statisticsData.getAllTransactionsCount().equals((long) transaction.getRecords().size());
    }
}
