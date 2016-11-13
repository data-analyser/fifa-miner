package com.fifaminer.service.transaction.impl;

import com.fifaminer.entity.Transaction;
import com.fifaminer.entity.Transaction.TransactionRecord;
import com.fifaminer.repository.TransactionRepository;
import com.fifaminer.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findRecordsWhereTimestampBetween(Long startTime, Long endTime) {
        return transactionRepository.findAll()
                .stream()
                .map(createTransactionWithRecordsInTimeRange(startTime, endTime))
                .collect(toList());
    }

    private Function<Transaction, Transaction> createTransactionWithRecordsInTimeRange(Long startTime, Long endTime) {
        return transaction -> new Transaction(
                transaction.getPlayerId(), filterByTimeRange(transaction.getRecords(), startTime, endTime)
        );
    }

    private List<TransactionRecord> filterByTimeRange(List<TransactionRecord> records,
                                                      Long startTime, Long endTime) {
        return records.stream()
                .filter(record -> record.getTimestamp() >= startTime && record.getTimestamp() <= endTime)
                .collect(toList());
    }
}
