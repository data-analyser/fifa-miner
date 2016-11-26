package com.fifaminer.client;

import com.fifaminer.client.dto.*;
import com.fifaminer.client.dto.strategy.MaxBuyStrategy;
import com.fifaminer.client.dto.strategy.PriceStrategy;
import com.fifaminer.client.dto.strategy.SellBuyNowStrategy;
import com.fifaminer.client.dto.strategy.SellStartStrategy;

import java.util.List;

public interface FifaMinerClient {

    Integer getMaxBuyPrice(Long playerId);

    Integer getSellStartPrice(Long playerId);

    Integer getSellBuyNowPrice(Long playerId);

    Integer getStartPriceProfit(Long playerId);

    Integer getBuyNowPriceProfit(Long playerId);

    PlayerPriceTO getPricesSummary(Long playerId);

    List<PlayerPriceTO> getPricesSummaryForPlayers(List<Long> playerIds);

    List<Long> findPlayersByTransactionsAnalyse(Long startTime, Long endTime, OrderingTypeTO orderingTypeTO, String groupName, Integer limit);

    List<Long> findPlayersByTransactionsAnalyse(Duration duration, OrderingTypeTO orderingTypeTO, String groupName, Integer limit);

    PriceLimits getPriceLimits(Long playerId, Platform platform);

    boolean isHealthy();

    boolean isPriceDistributionActual(Long playerId);

    void enableMaxBuyPriceStrategy(MaxBuyStrategy maxBuyStrategy);

    void enableSellStartPriceStrategy(SellStartStrategy sellStartStrategy);

    void enableSellBuyNowPriceStrategy(SellBuyNowStrategy sellBuyNowStrategy);

    String getActiveStrategy(PriceStrategy priceStrategy);
}

