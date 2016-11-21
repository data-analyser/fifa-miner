package com.fifaminer.client.impl;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fifaminer.client.FifaMinerClient;
import com.fifaminer.client.dto.*;
import com.fifaminer.client.dto.strategy.MaxBuyStrategy;
import com.fifaminer.client.dto.strategy.PriceStrategy;
import com.fifaminer.client.dto.strategy.SellBuyNowStrategy;
import com.fifaminer.client.dto.strategy.SellStartStrategy;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URL;
import java.util.List;

import static com.fifaminer.client.dto.strategy.PriceStrategy.*;
import static com.fifaminer.client.util.Strings.isNullOrEmpty;

public class FifaMinerClientImpl implements FifaMinerClient {

    private final Client client;
    private final String protocol;
    private final String serverUrl;
    private final Integer port;

    FifaMinerClientImpl(String protocol,
                        String serverUrl,
                        Integer port) {
        this.client = Client.create(getDefaultClientConfig());
        this.protocol = protocol;
        this.serverUrl = serverUrl;
        this.port = port;
    }

    @Override
    public Integer getMaxBuyPrice(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/max-buy"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public Integer getSellStartPrice(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/sell-start"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public Integer getSellBuyNowPrice(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/sell-buy-now"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public Integer getStartPriceProfit(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/start-profit"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public Integer getBuyNowPriceProfit(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/buy-now-profit"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public PlayerPriceTO getPricesSummary(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/summary"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(PlayerPriceTO.class);
    }

    @Override
    public List<PlayerPriceTO> getPricesSummaryForPlayers(List<Long> playerIds) {
        return client.resource(getUrl("/prices/summary"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(new GenericType<List<PlayerPriceTO>>() {}, playerIds);
    }

    @Override
    public List<Long> findPlayersByTransactionsAnalyse(Long startTime,
                                                       Long endTime,
                                                       OrderingTypeTO orderingTypeTO,
                                                       Integer limit) {
        return client.resource(getUrl(buildPlayerTransactionsRequestUrl(startTime, endTime, orderingTypeTO, limit)))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Long>>() {});
    }

    @Override
    public List<Long> findPlayersByTransactionsAnalyse(Duration duration, OrderingTypeTO orderingTypeTO, Integer limit) {
        return client.resource(getUrl(buildDurationPlayerTransactionsRequestUrl(duration, orderingTypeTO, limit)))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Long>>() {});
    }

    @Override
    public PriceLimits getPriceLimits(Long playerId, Platform platform) {
        return client.resource(getUrl("/prices/" + playerId + "/limits?platform=" + platform))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(PriceLimits.class);
    }

    @Override
    public boolean isHealthy() {
        try {
            String healthyPhrase = client.resource(getUrl("/health-check"))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .get(String.class);
            return !isNullOrEmpty(healthyPhrase);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPriceDistributionActual(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/is-actual"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Boolean.class);
    }

    @Override
    public void enableMaxBuyPriceStrategy(MaxBuyStrategy maxBuyStrategy) {
        enableStrategy(MAX_BUY_PRICE.name(), maxBuyStrategy.getStrategyName());
    }

    @Override
    public void enableSellStartPriceStrategy(SellStartStrategy sellStartStrategy) {
        enableStrategy(SELL_START_PRICE.name(), sellStartStrategy.getStrategyName());
    }

    @Override
    public void enableSellBuyNowPriceStrategy(SellBuyNowStrategy sellBuyNowStrategy) {
        enableStrategy(SELL_BUY_NOW_PRICE.name(), sellBuyNowStrategy.getStrategyName());
    }

    @Override
    public String getActiveStrategy(PriceStrategy priceStrategy) {
        return client.resource(getUrl("/settings/" + priceStrategy.name()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
    }

    private void enableStrategy(String strategyType, String strategyName) {
        client.resource(getUrl("/settings"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .put(new SettingConfigurationTO(strategyType, strategyName));
    }

    private String buildDurationPlayerTransactionsRequestUrl(Duration duration,
                                                             OrderingTypeTO orderingTypeTO,
                                                             Integer limit) {
        return UriBuilder.fromPath("/player-transactions/duration")
                .queryParam("duration", duration)
                .queryParam("orderingType", orderingTypeTO)
                .queryParam("limit", limit)
                .build()
                .toString();
    }

    private String buildPlayerTransactionsRequestUrl(Long startTime,
                                                     Long endTime,
                                                     OrderingTypeTO orderingTypeTO,
                                                     Integer limit) {
        return UriBuilder.fromPath("/player-transactions/range")
                .queryParam("startTime", startTime)
                .queryParam("endTime", endTime)
                .queryParam("orderingType", orderingTypeTO)
                .queryParam("limit", limit)
                .build()
                .toString();
    }

    private String getUrl(String urlPath) {
        try {
            return new URL(protocol, serverUrl, port, urlPath).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ClientConfig getDefaultClientConfig() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        return clientConfig;
    }
}
