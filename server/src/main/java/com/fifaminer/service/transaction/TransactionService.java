package com.fifaminer.service.transaction;

import com.fifaminer.entity.Transaction;

public interface TransactionService {

    Transaction findByPlayerId(Long playerId);
}
