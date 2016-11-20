package com.fifaminer.client.impl;

import com.fifaminer.client.FifaMinerClient;
import com.fifaminer.client.dto.Duration;
import com.fifaminer.client.dto.OrderingTypeTO;
import com.fifaminer.client.dto.Platform;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.junit.Ignore;
import org.junit.Test;

public class FifaMinerClientImplTest {

    private FifaMinerClient fifaMinerClient = new FifaMinerClientBuilder()
            .withProtocol("http")
            .withServerUrl("localhost")
            .withPort(8080)
            .build();

    @Test
    @Ignore
    public void testCalls() {
        fifaMinerClient.isHealthy();
        fifaMinerClient.getMaxBuyPrice(20800L);
        fifaMinerClient.getSellStartPrice(20800L);
        fifaMinerClient.getSellBuyNowPrice(20800L);
        fifaMinerClient.getStartPriceProfit(20800L);
        fifaMinerClient.getBuyNowPriceProfit(20800L);
        fifaMinerClient.getPricesSummary(20800L);
        fifaMinerClient.getPriceLimits(20800L, Platform.PC);
        fifaMinerClient.findPlayersByTransactionsAnalyse(
                1477574603286L, 1479064949173L, OrderingTypeTO.MAX_SELLS, 5
        );
        fifaMinerClient.findPlayersByTransactionsAnalyse(Duration.TODAY, OrderingTypeTO.MAX_SELLS, 5);
        fifaMinerClient.isPriceDistributionActual(20800L);
        fifaMinerClient.getPricesSummaryForPlayers(
                ImmutableList.of(172879L, 193352L, 164468L, 183285L, 152554L)
        );
    }
}