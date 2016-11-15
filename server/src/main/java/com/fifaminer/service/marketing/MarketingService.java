package com.fifaminer.service.marketing;

import com.fifaminer.service.marketing.type.OrderingType;

import java.util.List;

public interface MarketingService {

    List<Long> findPlayersByTransactionAnalyse(Long startTime, Long endTime, OrderingType orderingType, Integer limit);
}
