package com.fifaminer.service.transaction;

import com.fifaminer.entity.TransactionStatistics;

public interface TransactionAnalysingService {

    TransactionStatistics analyse(Long playerId);

    void saveStatistics(TransactionStatistics transactionStatistics);
}
