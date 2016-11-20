package com.fifaminer.service.transaction;

import com.fifaminer.entity.Transaction;
import com.fifaminer.service.transaction.model.TransactionStatistics;

public interface TransactionAnalysingService {

    TransactionStatistics analyse(Transaction transaction);
}
