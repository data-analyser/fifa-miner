package com.fifaminer.service.marketing.strategy;

import com.fifaminer.entity.TransactionStatistics;
import com.fifaminer.service.marketing.type.OrderingType;
import org.springframework.stereotype.Component;

import java.util.Comparator;

import static com.fifaminer.service.marketing.type.OrderingType.MAX_SELLS;
import static com.google.common.collect.Iterables.getLast;

@Component
public class PlayerSortingFactory {

    public Comparator<TransactionStatistics> create(OrderingType orderingType) {
        return orderingType.equals(MAX_SELLS) ? maxSells() : minRelists();
    }

    private Comparator<TransactionStatistics> minRelists() {
        return (current, next) -> Integer.compare(getLast(current.getStatisticsData()).getRelistPercents(), getLast(next.getStatisticsData()).getRelistPercents());
    }

    private Comparator<TransactionStatistics> maxSells() {
        return minSellsComparator().reversed();
    }

    private Comparator<TransactionStatistics> minSellsComparator() {
        return (current, next) -> Integer.compare(getLast(current.getStatisticsData()).getSellPercents(), getLast(next.getStatisticsData()).getSellPercents());
    }
}
