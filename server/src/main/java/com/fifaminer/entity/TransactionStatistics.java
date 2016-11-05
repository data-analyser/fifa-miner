package com.fifaminer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "transaction_analyse")
public final class TransactionStatistics {

    @Id
    private final Long playerId;
    private final Long analyseTime;
    private final Long buyCount;
    private final Long placedToMarkedCount;
    private final Long sellCount;
    private final Long relistCount;
    private final Long allTransactionsCount;
    private final Integer relistPercents;
    private final Integer medianSellTime;
    private final Integer medianSellPrice;
}
