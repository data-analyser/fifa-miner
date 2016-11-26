package com.fifaminer.service.group;

import com.fifaminer.entity.PlayerProfile;
import com.fifaminer.entity.pojo.Attributes;
import com.fifaminer.client.dto.PlayerAttribute;
import com.google.common.collect.ImmutableMap.Builder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;

import static com.fifaminer.client.dto.PlayerAttribute.PACE;
import static org.apache.commons.lang3.math.NumberUtils.DOUBLE_ZERO;

@Component
public class AttributeExtractor {

    private Map<PlayerAttribute, Function<PlayerProfile, Double>> attributeExtractors;

    @PostConstruct
    private void initExtractors() {
        attributeExtractors = new Builder<PlayerAttribute, Function<PlayerProfile, Double>>()
                .put(PACE, extract("fut.attribute.PAC"))
                .build();
    }

    public Double getAttribute(PlayerProfile playerProfile, PlayerAttribute playerAttribute) {
        return attributeExtractors.get(playerAttribute)
                .apply(playerProfile);
    }

    Function<PlayerProfile, Double> extract(String attributeName) {
        return playerProfile -> playerProfile.getAttributes()
                .stream()
                .filter(attribute -> attribute.getName().equals(attributeName))
                .map(Attributes::getValue)
                .findFirst()
                .orElse(DOUBLE_ZERO);
    }
}
