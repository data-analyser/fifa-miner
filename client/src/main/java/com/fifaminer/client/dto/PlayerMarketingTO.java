package com.fifaminer.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class PlayerMarketingTO {

    private final Long playerId;
    private final Integer buyPrice;
    private final Integer sellPrice;

    @JsonCreator
    public PlayerMarketingTO(@JsonProperty("playerId") Long playerId,
                             @JsonProperty("buyPrice") Integer buyPrice,
                             @JsonProperty("sellPrice") Integer sellPrice) {
        this.playerId = playerId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerMarketingTO that = (PlayerMarketingTO) o;

        if (playerId != null ? !playerId.equals(that.playerId) : that.playerId != null) return false;
        if (buyPrice != null ? !buyPrice.equals(that.buyPrice) : that.buyPrice != null) return false;
        return sellPrice != null ? sellPrice.equals(that.sellPrice) : that.sellPrice == null;

    }

    @Override
    public int hashCode() {
        int result = playerId != null ? playerId.hashCode() : 0;
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (sellPrice != null ? sellPrice.hashCode() : 0);
        return result;
    }
}


