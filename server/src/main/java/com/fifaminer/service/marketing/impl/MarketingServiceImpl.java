package com.fifaminer.service.marketing.impl;

import com.fifaminer.service.marketing.MarketingService;
import com.fifaminer.service.marketing.model.PlayerMarketing;
import com.fifaminer.service.price.PriceService;
import com.fifaminer.service.transaction.TransactionAnalysingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
public class MarketingServiceImpl implements MarketingService {

    private final TransactionAnalysingService transactionAnalysingService;
    private final PriceService priceService;

    private static final int PLAYERS_LIMIT = 100;

    @Autowired
    public MarketingServiceImpl(TransactionAnalysingService transactionAnalysingService, PriceService priceService) {
        this.transactionAnalysingService = transactionAnalysingService;
        this.priceService = priceService;
    }

    @Override
    public List<PlayerMarketing> findPlayersForMarketing() {
        return transactionAnalysingService.findAll()
                .stream()
                .sorted((current, next) -> Integer.compare(current.getRelistPercents(), next.getRelistPercents()))
                .map(statistics -> new PlayerMarketing(
                        statistics.getPlayerId(),
                        priceService.getBuyPrice(statistics.getPlayerId()),
                        priceService.getSellPrice(statistics.getPlayerId())))
                .filter(hasValidMarketingData())
                .limit(PLAYERS_LIMIT)
                .collect(toList());
    }

    private Predicate<PlayerMarketing> hasValidMarketingData() {
        return playerMarketing -> (!playerMarketing.getBuyPrice().equals(INTEGER_ZERO) && !playerMarketing.getSellPrice().equals(INTEGER_ZERO))
                && (playerMarketing.getBuyPrice() < playerMarketing.getSellPrice());
    }
}
