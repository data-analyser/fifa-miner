package com.fifaminer.service.transaction;

import com.fifaminer.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAll();
}
