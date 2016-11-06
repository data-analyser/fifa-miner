package com.fifaminer.entity;

import com.fifaminer.entity.pojo.TransactionStatisticsData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document(collection = "transactions_analyse")
public final class TransactionStatistics {

    @Id
    private final Long playerId;

    private final List<TransactionStatisticsData> statisticsData;
}
