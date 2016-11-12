package com.fifaminer.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class PlayerPriceTO {

    private final Long playerId;
    private final Integer buyPrice;
    private final Integer sellPrice;
    private final Integer profit;
    private final String buyPriceStrategy;
    private final String sellPriceStrategy;

    @JsonCreator
    public PlayerPriceTO(@JsonProperty("playerId") Long playerId,
                         @JsonProperty("buyPrice") Integer buyPrice,
                         @JsonProperty("sellPrice") Integer sellPrice,
                         @JsonProperty("profit") Integer profit,
                         @JsonProperty("buyPriceStrategy") String buyPriceStrategy,
                         @JsonProperty("sellPriceStrategy") String sellPriceStrategy) {
        this.playerId = playerId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.profit = profit;
        this.buyPriceStrategy = buyPriceStrategy;
        this.sellPriceStrategy = sellPriceStrategy;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public Integer getProfit() {
        return profit;
    }

    public String getBuyPriceStrategy() {
        return buyPriceStrategy;
    }

    public String getSellPriceStrategy() {
        return sellPriceStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerPriceTO playerPriceTO = (PlayerPriceTO) o;

        if (playerId != null ? !playerId.equals(playerPriceTO.playerId) : playerPriceTO.playerId != null) return false;
        if (buyPrice != null ? !buyPrice.equals(playerPriceTO.buyPrice) : playerPriceTO.buyPrice != null) return false;
        if (sellPrice != null ? !sellPrice.equals(playerPriceTO.sellPrice) : playerPriceTO.sellPrice != null)
            return false;
        if (profit != null ? !profit.equals(playerPriceTO.profit) : playerPriceTO.profit != null) return false;
        if (buyPriceStrategy != null ? !buyPriceStrategy.equals(playerPriceTO.buyPriceStrategy) : playerPriceTO.buyPriceStrategy != null)
            return false;
        return sellPriceStrategy != null ? sellPriceStrategy.equals(playerPriceTO.sellPriceStrategy) : playerPriceTO.sellPriceStrategy == null;

    }

    @Override
    public int hashCode() {
        int result = playerId != null ? playerId.hashCode() : 0;
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (sellPrice != null ? sellPrice.hashCode() : 0);
        result = 31 * result + (profit != null ? profit.hashCode() : 0);
        result = 31 * result + (buyPriceStrategy != null ? buyPriceStrategy.hashCode() : 0);
        result = 31 * result + (sellPriceStrategy != null ? sellPriceStrategy.hashCode() : 0);
        return result;
    }
}
