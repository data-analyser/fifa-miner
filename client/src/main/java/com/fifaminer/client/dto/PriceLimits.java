package com.fifaminer.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class PriceLimits {

    private final Long playerId;
    private final Integer minPrice;
    private final Integer maxPrice;

    @JsonCreator
    public PriceLimits(@JsonProperty("playerId") Long playerId,
                       @JsonProperty("minPrice") Integer minPrice,
                       @JsonProperty("maxPrice") Integer maxPrice) {
        this.playerId = playerId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceLimits that = (PriceLimits) o;

        if (playerId != null ? !playerId.equals(that.playerId) : that.playerId != null) return false;
        if (minPrice != null ? !minPrice.equals(that.minPrice) : that.minPrice != null) return false;
        return maxPrice != null ? maxPrice.equals(that.maxPrice) : that.maxPrice == null;

    }

    @Override
    public int hashCode() {
        int result = playerId != null ? playerId.hashCode() : 0;
        result = 31 * result + (minPrice != null ? minPrice.hashCode() : 0);
        result = 31 * result + (maxPrice != null ? maxPrice.hashCode() : 0);
        return result;
    }
}
