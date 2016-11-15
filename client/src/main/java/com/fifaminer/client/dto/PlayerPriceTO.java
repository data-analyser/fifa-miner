package com.fifaminer.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class PlayerPriceTO {

    private final Long playerId;
    private final Integer maxBuyPrice;
    private final Integer sellStartPrice;
    private final Integer sellBuyNowPrice;
    private final Integer profit;
    private final String maxBuyPriceStrategy;
    private final String sellStartPriceStrategy;
    private final String sellBuyNowPriceStrategy;

    @JsonCreator
    public PlayerPriceTO(@JsonProperty("playerId") Long playerId,
                         @JsonProperty("maxBuyPrice") Integer maxBuyPrice,
                         @JsonProperty("sellStartPrice") Integer sellStartPrice,
                         @JsonProperty("sellBuyNowPrice") Integer sellBuyNowPrice,
                         @JsonProperty("profit") Integer profit,
                         @JsonProperty("maxBuyPriceStrategy") String maxBuyPriceStrategy,
                         @JsonProperty("sellStartPriceStrategy") String sellStartPriceStrategy,
                         @JsonProperty("sellBuyNowPriceStrategy") String sellBuyNowPriceStrategy) {
        this.playerId = playerId;
        this.maxBuyPrice = maxBuyPrice;
        this.sellStartPrice = sellStartPrice;
        this.sellBuyNowPrice = sellBuyNowPrice;
        this.profit = profit;
        this.maxBuyPriceStrategy = maxBuyPriceStrategy;
        this.sellStartPriceStrategy = sellStartPriceStrategy;
        this.sellBuyNowPriceStrategy = sellBuyNowPriceStrategy;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Integer getMaxBuyPrice() {
        return maxBuyPrice;
    }

    public Integer getSellStartPrice() {
        return sellStartPrice;
    }

    public Integer getSellBuyNowPrice() {
        return sellBuyNowPrice;
    }

    public Integer getProfit() {
        return profit;
    }

    public String getMaxBuyPriceStrategy() {
        return maxBuyPriceStrategy;
    }

    public String getSellStartPriceStrategy() {
        return sellStartPriceStrategy;
    }

    public String getSellBuyNowPriceStrategy() {
        return sellBuyNowPriceStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerPriceTO that = (PlayerPriceTO) o;

        if (playerId != null ? !playerId.equals(that.playerId) : that.playerId != null) return false;
        if (maxBuyPrice != null ? !maxBuyPrice.equals(that.maxBuyPrice) : that.maxBuyPrice != null) return false;
        if (sellStartPrice != null ? !sellStartPrice.equals(that.sellStartPrice) : that.sellStartPrice != null) return false;
        if (sellBuyNowPrice != null ? !sellBuyNowPrice.equals(that.sellBuyNowPrice) : that.sellBuyNowPrice != null) return false;
        if (profit != null ? !profit.equals(that.profit) : that.profit != null) return false;
        if (maxBuyPriceStrategy != null ? !maxBuyPriceStrategy.equals(that.maxBuyPriceStrategy) : that.maxBuyPriceStrategy != null)
            return false;
        if (sellStartPriceStrategy != null ? !sellStartPriceStrategy.equals(that.sellStartPriceStrategy) : that.sellStartPriceStrategy != null)
            return false;
        return sellBuyNowPriceStrategy != null ? sellBuyNowPriceStrategy.equals(that.sellBuyNowPriceStrategy) : that.sellBuyNowPriceStrategy == null;

    }

    @Override
    public int hashCode() {
        int result = playerId != null ? playerId.hashCode() : 0;
        result = 31 * result + (maxBuyPrice != null ? maxBuyPrice.hashCode() : 0);
        result = 31 * result + (sellStartPrice != null ? sellStartPrice.hashCode() : 0);
        result = 31 * result + (sellBuyNowPrice != null ? sellBuyNowPrice.hashCode() : 0);
        result = 31 * result + (profit != null ? profit.hashCode() : 0);
        result = 31 * result + (maxBuyPriceStrategy != null ? maxBuyPriceStrategy.hashCode() : 0);
        result = 31 * result + (sellStartPriceStrategy != null ? sellStartPriceStrategy.hashCode() : 0);
        result = 31 * result + (sellBuyNowPriceStrategy != null ? sellBuyNowPriceStrategy.hashCode() : 0);
        return result;
    }
}
