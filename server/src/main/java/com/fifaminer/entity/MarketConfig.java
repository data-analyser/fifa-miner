package com.fifaminer.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "config")
public class MarketConfig {

    @Id
    private Integer id;

    private Set<String> activeTags = new HashSet<>();
}