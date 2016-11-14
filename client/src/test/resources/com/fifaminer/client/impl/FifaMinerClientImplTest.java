package com.fifaminer.client.impl;

import com.fifaminer.client.FifaMinerClient;
import com.fifaminer.client.dto.Platform;
import com.fifaminer.client.dto.PriceLimits;
import org.junit.Test;

public class FifaMinerClientImplTest {

    private FifaMinerClient fifaMinerClient = new FifaMinerClientBuilder()
            .withProtocol("http")
            .withServerUrl("localhost")
            .withPort(8080)
            .build();

    @Test
    public void getBuyPrice() {
        PriceLimits price = fifaMinerClient.getPriceLimits(20800L, Platform.PC);
    }
}