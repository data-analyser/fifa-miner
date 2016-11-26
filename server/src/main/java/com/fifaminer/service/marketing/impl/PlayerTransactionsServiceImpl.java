package com.fifaminer.service.marketing.impl;

import com.fifaminer.client.dto.Duration;
import com.fifaminer.service.common.TimeRangeService;
import com.fifaminer.service.common.model.TimeRange;
import com.fifaminer.service.group.PlayerGroupService;
import com.fifaminer.service.marketing.PlayerTransactionsService;
import com.fifaminer.service.marketing.strategy.PlayerSortingFactory;
import com.fifaminer.service.marketing.type.OrderingType;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import com.fifaminer.service.transaction.TransactionService;
import com.fifaminer.service.transaction.model.TransactionStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class PlayerTransactionsServiceImpl implements PlayerTransactionsService {

    private final TransactionAnalysingService transactionAnalysingService;
    private final TransactionService transactionService;
    private final PlayerSortingFactory sortingTypeFactory;
    private final TimeRangeService timeRangeService;
    private final PlayerGroupService playerGroupService;

    private static final Integer MIN_TRANSACTIONS_COUNT_FOR_ANALYSE = 10;

    @Autowired
    public PlayerTransactionsServiceImpl(TransactionAnalysingService transactionAnalysingService,
                                         TransactionService transactionService,
                                         PlayerSortingFactory sortingTypeFactory,
                                         TimeRangeService timeRangeService,
                                         PlayerGroupService playerGroupService) {
        this.transactionAnalysingService = transactionAnalysingService;
        this.transactionService = transactionService;
        this.sortingTypeFactory = sortingTypeFactory;
        this.timeRangeService = timeRangeService;
        this.playerGroupService = playerGroupService;
    }

    @Override
    public List<Long> findPlayers(Long startTime,
                                  Long endTime,
                                  OrderingType orderingType,
                                  String groupName,
                                  Integer limit) {
        log.info("Select players by transactions analyse start time = {}, end time = {}, ordering type = {}, groupName = {}, limit = {}",
                startTime, endTime, orderingType, groupName, limit);

        List<Long> playersByGroup = playerGroupService.findPlayersByGroup(groupName);
        return transactionService.findRecordsWhereTimestampBetween(startTime, endTime)
                .stream()
                .filter(transaction -> playersByGroup.contains(transaction.getPlayerId()))
                .filter(transaction -> transaction.getRecords().size() >= MIN_TRANSACTIONS_COUNT_FOR_ANALYSE)
                .map(transactionAnalysingService::analyse)
                .sorted(sortingTypeFactory.create(orderingType))
                .map(TransactionStatistics::getPlayerId)
                .limit(limit)
                .collect(toList());
    }

    @Override
    public List<Long> findPlayers(Duration duration,
                                  OrderingType orderingType,
                                  String groupName,
                                  Integer limit) {
        log.info("Select players by transactions analyse duration = {}, ordering type = {}, groupName = {}, limit = {}",
                duration, orderingType, groupName, limit);
        TimeRange timeRange = timeRangeService.fromDuration(duration);
        return findPlayers(
                timeRange.getStartTime(), timeRange.getEndTime(), orderingType, groupName, limit
        );
    }
}
