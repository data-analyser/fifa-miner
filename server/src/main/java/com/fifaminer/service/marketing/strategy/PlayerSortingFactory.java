package com.fifaminer.service.marketing.strategy;

import com.fifaminer.service.marketing.type.OrderingType;
import com.fifaminer.service.transaction.model.TransactionStatistics;
import org.springframework.stereotype.Component;

import java.util.Comparator;

import static com.fifaminer.service.marketing.type.OrderingType.MAX_SELLS;

@Component
public class PlayerSortingFactory {

    public Comparator<TransactionStatistics> create(OrderingType orderingType) {
        return orderingType.equals(MAX_SELLS) ? maxSells() : minRelists();
    }

    private Comparator<TransactionStatistics> minRelists() {
        return (current, next) -> Integer.compare(current.getRelistPercents(), next.getRelistPercents());
    }

    private Comparator<TransactionStatistics> maxSells() {
        return minSellsComparator().reversed();
    }

    private Comparator<TransactionStatistics> minSellsComparator() {
        return (current, next) -> Integer.compare(current.getSellPercents(), next.getSellPercents());
    }
}
