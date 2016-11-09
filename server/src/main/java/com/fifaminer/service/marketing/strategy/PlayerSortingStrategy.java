package com.fifaminer.service.marketing.strategy;

import com.fifaminer.entity.TransactionStatistics;

import java.util.Comparator;

import static com.google.common.collect.Iterables.getLast;

public class PlayerSortingStrategy {

    public static Comparator<TransactionStatistics> minRelists() {
        return (current, next) -> Integer.compare(getLast(current.getStatisticsData()).getRelistPercents(), getLast(next.getStatisticsData()).getRelistPercents());
    }

    public static Comparator<TransactionStatistics> maxSells() {
        return minSellsComparator().reversed();
    }

    private static Comparator<TransactionStatistics> minSellsComparator() {
        return (current, next) -> Integer.compare(getLast(current.getStatisticsData()).getSellPercents(), getLast(next.getStatisticsData()).getSellPercents());
    }
}
