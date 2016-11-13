package com.fifaminer.service.marketing;

import com.fifaminer.service.marketing.type.OrderingType;
import com.fifaminer.service.price.model.PlayerPrice;

import java.util.List;

public interface MarketingService {

    List<PlayerPrice> findPlayersByTransactionAnalyse(Long startTime, Long endTime, OrderingType orderingType, Integer limit);
}
