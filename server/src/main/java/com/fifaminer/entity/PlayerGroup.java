package com.fifaminer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@Document(collection = "player_group")
public class PlayerGroup {

    @Id
    private final String groupName;
    private final Set<Long> playerIds;
}
