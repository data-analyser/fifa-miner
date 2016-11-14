package com.fifaminer.easports.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fifaminer.easports.client.EaSportsClient;
import com.fifaminer.easports.model.PlayerPriceLimits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Component
public class EaSportsClientImpl implements EaSportsClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String PROTOCOL = "https";
    private static final String EA_BASE_URL = "www.easports.com";
    private static final String PRICE_LIMITS_URL = "/uk/fifa/ultimate-team/api/fut/price-band/";

    @Autowired
    public EaSportsClientImpl(RestTemplate restTemplate,
                              ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public PlayerPriceLimits getPriceLimits(Long playerId) {
        String jsonResponse = restTemplate.getForEntity(buildUrl(PRICE_LIMITS_URL + playerId), String.class)
                .getBody();
        return readPriceLimits(playerId, jsonResponse);
    }

    private PlayerPriceLimits readPriceLimits(Long playerId, String jsonResponse) {
        try {
            Map<String, PlayerPriceLimits> limits = objectMapper.readValue(
                    jsonResponse, new TypeReference<Map<String, PlayerPriceLimits>>() {}
            );
            return limits.get(playerId.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildUrl(String path) {
        try {
            return new URL(PROTOCOL, EA_BASE_URL, path).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
