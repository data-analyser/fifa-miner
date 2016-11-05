package com.fifaminer.service.transaction.impl;

import com.fifaminer.entity.Transaction;
import com.fifaminer.repository.TransactionRepository;
import com.fifaminer.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction findByPlayerId(Long playerId) {
        return transactionRepository.findOne(playerId);
    }
}
