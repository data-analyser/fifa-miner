package com.fifaminer.client.impl;

import com.fifaminer.client.FifaMinerClient;
import com.fifaminer.client.dto.Platform;
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
        fifaMinerClient.getProfit(20800L);
        fifaMinerClient.getPricesSummary(20800L);
        fifaMinerClient.getPriceLimits(20800L, Platform.PC);
    }
}