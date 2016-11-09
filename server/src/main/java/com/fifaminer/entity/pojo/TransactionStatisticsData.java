package com.fifaminer.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionStatisticsData {

    private final Long analyseTime;
    private final Long buyCount;
    private final Long placedToMarkedCount;
    private final Long sellCount;
    private final Long relistCount;
    private final Long allTransactionsCount;
    private final Integer sellPercents;
    private final Integer relistPercents;
    private final Integer medianSellTime;
    private final Integer medianSellPrice;
}
