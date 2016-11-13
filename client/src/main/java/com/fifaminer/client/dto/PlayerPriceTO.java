package com.fifaminer.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class PlayerPriceTO {

    private final Long playerId;
    private final Integer buyPrice;
    private final Integer sellPrice;
    private final Integer bidPrice;
    private final Integer profit;
    private final String buyPriceStrategy;
    private final String sellPriceStrategy;
    private final String bidPriceStrategy;

    @JsonCreator
    public PlayerPriceTO(@JsonProperty("playerId") Long playerId,
                         @JsonProperty("buyPrice") Integer buyPrice,
                         @JsonProperty("sellPrice") Integer sellPrice,
                         @JsonProperty("bidPrice") Integer bidPrice,
                         @JsonProperty("profit") Integer profit,
                         @JsonProperty("buyPriceStrategy") String buyPriceStrategy,
                         @JsonProperty("sellPriceStrategy") String sellPriceStrategy,
                         @JsonProperty("bidPriceStrategy") String bidPriceStrategy) {
        this.playerId = playerId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.bidPrice = bidPrice;
        this.profit = profit;
        this.buyPriceStrategy = buyPriceStrategy;
        this.sellPriceStrategy = sellPriceStrategy;
        this.bidPriceStrategy = bidPriceStrategy;
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

    public Integer getBidPrice() {
        return bidPrice;
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

    public String getBidPriceStrategy() {
        return bidPriceStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerPriceTO that = (PlayerPriceTO) o;

        if (playerId != null ? !playerId.equals(that.playerId) : that.playerId != null) return false;
        if (buyPrice != null ? !buyPrice.equals(that.buyPrice) : that.buyPrice != null) return false;
        if (sellPrice != null ? !sellPrice.equals(that.sellPrice) : that.sellPrice != null) return false;
        if (bidPrice != null ? !bidPrice.equals(that.bidPrice) : that.bidPrice != null) return false;
        if (profit != null ? !profit.equals(that.profit) : that.profit != null) return false;
        if (buyPriceStrategy != null ? !buyPriceStrategy.equals(that.buyPriceStrategy) : that.buyPriceStrategy != null)
            return false;
        if (sellPriceStrategy != null ? !sellPriceStrategy.equals(that.sellPriceStrategy) : that.sellPriceStrategy != null)
            return false;
        return bidPriceStrategy != null ? bidPriceStrategy.equals(that.bidPriceStrategy) : that.bidPriceStrategy == null;

    }

    @Override
    public int hashCode() {
        int result = playerId != null ? playerId.hashCode() : 0;
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (sellPrice != null ? sellPrice.hashCode() : 0);
        result = 31 * result + (bidPrice != null ? bidPrice.hashCode() : 0);
        result = 31 * result + (profit != null ? profit.hashCode() : 0);
        result = 31 * result + (buyPriceStrategy != null ? buyPriceStrategy.hashCode() : 0);
        result = 31 * result + (sellPriceStrategy != null ? sellPriceStrategy.hashCode() : 0);
        result = 31 * result + (bidPriceStrategy != null ? bidPriceStrategy.hashCode() : 0);
        return result;
    }
}
