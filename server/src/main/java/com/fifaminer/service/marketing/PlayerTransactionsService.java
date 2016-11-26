package com.fifaminer.service.marketing;

import com.fifaminer.client.dto.Duration;
import com.fifaminer.service.marketing.type.OrderingType;

import java.util.List;

public interface PlayerTransactionsService {

    List<Long> findPlayers(Long startTime, Long endTime, OrderingType orderingType, String groupName, Integer limit);

    List<Long> findPlayers(Duration duration, OrderingType orderingType, String groupName, Integer limit);
}
