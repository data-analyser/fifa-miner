package com.fifaminer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "tags")
@NoArgsConstructor
public class Tag implements Serializable {

    @Id
    private ObjectId id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }
}