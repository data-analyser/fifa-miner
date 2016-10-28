package com.fifaminer.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fifaminer.util.DataHelper.toReadableString;

@Data
@Document(collection = "player_statistic")
public class PlayerStatistic {

    private Long id;
    private Integer lastPrice;

    private LocalDateTime innerDate;

    @JsonView(Internal.class)
    public String getDate() {
        return toReadableString(innerDate);
    }

    private List<PriceDistribution> prices = new ArrayList<>();

    private Map<Long, HistoryPoint> history;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PriceDistribution {

        private Integer price;
        private Integer amount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HistoryPoint {

        private Long minPrice;
        private Long median;
    }

    public static class Public {

    }

    public static class Internal extends Public {

    }
}