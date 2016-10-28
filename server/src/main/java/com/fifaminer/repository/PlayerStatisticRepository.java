package com.fifaminer.repository;

import com.fifaminer.entity.PlayerStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatisticRepository extends MongoRepository<PlayerStatistic, Long> {

}