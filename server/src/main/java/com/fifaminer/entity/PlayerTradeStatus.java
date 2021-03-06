package com.fifaminer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.fifaminer.util.DataHelper.toReadableString;

@Data
@Document(collection = "player_trade_status")
@AllArgsConstructor
@NoArgsConstructor
public class PlayerTradeStatus {

    public PlayerTradeStatus(Long id, String name, Integer maxPrice) {
        this.id = id;
        this.name = name;
        this.maxPrice = maxPrice;
    }

    @Id
    private Long id;

    private int boughtAmount;

    private String name;

    private Integer maxPrice;

    private Long lastBuyPrice;

    private Integer sellStartPrice;

    private Integer rareflag;

    private Integer minMarketPrice;

    private Integer sellBuyNowPrice;

    private LocalDateTime innerDate;

    private String lastUpdate;

    private String lastDate;

    private boolean enabled = true;

    private Set<String> tags = new HashSet<>();

    public String getLastDate() {
        return toReadableString(innerDate);
    }

    public void updateDate() {
        innerDate = LocalDateTime.now();
    }
}