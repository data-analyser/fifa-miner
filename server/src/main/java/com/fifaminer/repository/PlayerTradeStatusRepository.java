package com.fifaminer.repository;

import com.fifaminer.entity.PlayerTradeStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerTradeStatusRepository extends MongoRepository<PlayerTradeStatus, Long> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<PlayerTradeStatus> findByName(String phrase);
}