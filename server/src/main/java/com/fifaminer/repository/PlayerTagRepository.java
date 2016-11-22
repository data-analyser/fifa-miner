package com.fifaminer.repository;

import com.fifaminer.entity.PlayerTag;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerTagRepository extends MongoRepository<PlayerTag, ObjectId> {
}
