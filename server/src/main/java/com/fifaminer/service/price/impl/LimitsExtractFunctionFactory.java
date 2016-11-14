package com.fifaminer.service.price.impl;

import com.fifaminer.client.dto.Platform;
import com.fifaminer.easports.model.Limits;
import com.fifaminer.easports.model.PlayerPriceLimits;
import org.spark_project.guava.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;

import static com.fifaminer.client.dto.Platform.*;

@Component
public class LimitsExtractFunctionFactory {

    private Map<Platform, Function<PlayerPriceLimits, Limits>> limitsExtractFunctions;

    @PostConstruct
    private void initLimitsExtractFunctions() {
        limitsExtractFunctions = ImmutableMap.of(
                PC, playerData -> playerData.getPriceLimits().getPc(),
                XBOX_ONE, playerData -> playerData.getPriceLimits().getXboxone(),
                PS4, playerData -> playerData.getPriceLimits().getPs4()
        );
    }

    public Function<PlayerPriceLimits, Limits> getExtractFunction(Platform platform) {
        return limitsExtractFunctions.get(platform);
    }
}
