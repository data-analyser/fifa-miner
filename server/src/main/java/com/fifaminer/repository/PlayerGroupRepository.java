package com.fifaminer.repository;

import com.fifaminer.entity.PlayerGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGroupRepository extends MongoRepository<PlayerGroup, String> {
}
