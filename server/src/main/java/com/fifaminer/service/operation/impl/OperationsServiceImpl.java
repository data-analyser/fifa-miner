package com.fifaminer.service.operation.impl;

import com.fifaminer.entity.TransactionStatistics;
import com.fifaminer.service.operation.OperationsService;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import com.fifaminer.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OperationsServiceImpl implements OperationsService {

    private final TransactionAnalysingService transactionAnalysingService;
    private final TransactionService transactionService;

    @Autowired
    public OperationsServiceImpl(TransactionAnalysingService transactionAnalysingService,
                                 TransactionService transactionService) {
        this.transactionAnalysingService = transactionAnalysingService;
        this.transactionService = transactionService;
    }

    @Async
    @Override
    public void runTransactionAnalyse() {
        List<TransactionStatistics> transactionStatistics = transactionService.findAll()
                .stream()
                .map(transactionAnalysingService::analyse)
                .collect(toList());

        transactionAnalysingService.saveAll(transactionStatistics);
    }
}
