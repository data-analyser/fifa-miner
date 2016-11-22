package com.fifaminer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@AllArgsConstructor
@Document(collection = "player_tag")
public final class PlayerTag {

    @Id
    private final ObjectId tagId;
    private final Set<Long> playerIds;
}
