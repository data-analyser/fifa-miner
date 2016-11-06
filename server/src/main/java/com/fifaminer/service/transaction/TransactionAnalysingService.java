package com.fifaminer.service.transaction;

import com.fifaminer.entity.Transaction;
import com.fifaminer.entity.TransactionStatistics;

import java.util.List;
import java.util.Optional;

public interface TransactionAnalysingService {

    TransactionStatistics analyse(Transaction transaction);

    void saveAll(List<TransactionStatistics> transactionStatistics);

    List<TransactionStatistics> findAll();

    Optional<TransactionStatistics> findByPlayerId(Long playerId);

    void save(TransactionStatistics transactionStatistics);
}
