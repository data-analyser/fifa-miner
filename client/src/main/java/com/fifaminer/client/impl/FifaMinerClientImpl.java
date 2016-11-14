package com.fifaminer.client.impl;

import com.fifaminer.client.FifaMinerClient;
import com.fifaminer.client.dto.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URL;
import java.util.List;

public class FifaMinerClientImpl implements FifaMinerClient {

    private final Client client;
    private final String protocol;
    private final String serverUrl;
    private final Integer port;

    FifaMinerClientImpl(String protocol,
                               String serverUrl,
                               Integer port) {
        this.client = Client.create();
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
    public Integer getBuyPrice(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/buy"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public Integer getSellPrice(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/sell"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(Integer.class);
    }

    @Override
    public Integer getBidPrice(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/bid"))
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
    public PlayerPriceTO getPriceSummary(Long playerId) {
        return client.resource(getUrl("/prices/" + playerId + "/summary"))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(PlayerPriceTO.class);
    }

    @Override
    public List<PlayerPriceTO> findPlayersByTransactionsAnalyse(Long startTime,
                                                                Long endTime,
                                                                OrderingTypeTO orderingTypeTO,
                                                                Integer limit) {
        return client.resource(getUrl(buildMarketingRequestUrl(startTime, endTime, orderingTypeTO, limit)))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<PlayerPriceTO>>(){});
    }

    @Override
    public PriceLimits getPriceLimits(Long playerId, Platform platform) {
        return client.resource(getUrl("/prices/" + playerId + "/limits?platform=" + platform))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(PriceLimits.class);
    }

    private String buildMarketingRequestUrl(Long startTime,
                                            Long endTime,
                                            OrderingTypeTO orderingTypeTO,
                                            Integer limit) {
        return UriBuilder.fromPath("/marketing")
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
}
