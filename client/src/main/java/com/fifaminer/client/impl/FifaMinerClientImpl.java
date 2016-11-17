package com.fifaminer.client.impl;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fifaminer.client.FifaMinerClient;
import com.fifaminer.client.dto.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URL;
import java.util.List;

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
    public void runForceTransactionAnalyse() {
        client.resource(getUrl("/operations/transaction-analyse"))
                .post();
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
    public Integer getProfit(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/profit"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public String getSetting(SettingTO settingTO) {
        return client.resource(getUrl("/settings/" + settingTO.name()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
    }

    @Override
    public void updateSetting(SettingConfigurationTO settingConfigurationTO) {
        client.resource(getUrl("/settings/"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .put(settingConfigurationTO);
    }

    @Override
    public PlayerPriceTO getPricesSummary(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/summary"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(PlayerPriceTO.class);
    }

    @Override
    public List<Long> findPlayersByTransactionsAnalyse(Long startTime,
                                                       Long endTime,
                                                       OrderingTypeTO orderingTypeTO,
                                                       Integer limit) {
        return client.resource(getUrl(buildMarketingRequestUrl(startTime, endTime, orderingTypeTO, limit)))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Long>>() {});
    }

    @Override
    public List<Long> findPlayersByTransactionsAnalyse(Duration duration, OrderingTypeTO orderingTypeTO, Integer limit) {
        return client.resource(getUrl(buildDurationMarketingRequestUrl(duration, orderingTypeTO, limit)))
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

    private String buildDurationMarketingRequestUrl(Duration duration,
                                                    OrderingTypeTO orderingTypeTO,
                                                    Integer limit) {
        return UriBuilder.fromPath("/marketing/duration")
                .queryParam("duration", duration)
                .queryParam("orderingType", orderingTypeTO)
                .queryParam("limit", limit)
                .build()
                .toString();
    }

    private String buildMarketingRequestUrl(Long startTime,
                                            Long endTime,
                                            OrderingTypeTO orderingTypeTO,
                                            Integer limit) {
        return UriBuilder.fromPath("/marketing/range")
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
