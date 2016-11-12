package com.fifaminer.client.impl;

import com.fifaminer.client.FifaMinerClient;
import org.junit.Test;

public class FifaMinerClientImplTest {

    private FifaMinerClient fifaMinerClient = new FifaMinerClientBuilder()
            .withProtocol("http")
            .withServerUrl("localhost")
            .withPort(8080)
            .build();

    @Test
    public void getBuyPrice() {
        Integer price = fifaMinerClient.getBuyPrice(20800L);
    }
}