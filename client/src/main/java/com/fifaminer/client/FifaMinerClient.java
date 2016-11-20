package com.fifaminer.client;

import com.fifaminer.client.dto.*;

import java.util.List;

public interface FifaMinerClient {

    void runForceTransactionAnalyse();

    Integer getMaxBuyPrice(Long playerId);

    Integer getSellStartPrice(Long playerId);

    Integer getSellBuyNowPrice(Long playerId);

    Integer getStartPriceProfit(Long playerId);

    Integer getBuyNowPriceProfit(Long playerId);

    String getSetting(SettingTO settingTO);

    void updateSetting(SettingConfigurationTO settingConfigurationTO);

    PlayerPriceTO getPricesSummary(Long playerId);

    List<PlayerPriceTO> getPricesSummaryForPlayers(List<Long> playerIds);

    List<Long> findPlayersByTransactionsAnalyse(Long startTime, Long endTime, OrderingTypeTO orderingTypeTO, Integer limit);

    List<Long> findPlayersByTransactionsAnalyse(Duration duration, OrderingTypeTO orderingTypeTO, Integer limit);

    PriceLimits getPriceLimits(Long playerId, Platform platform);

    boolean isHealthy();

    boolean isPriceDistributionActual(Long playerId);
}

