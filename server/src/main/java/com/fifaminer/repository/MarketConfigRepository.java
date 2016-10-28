package com.fifaminer.repository;

import com.fifaminer.entity.MarketConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketConfigRepository extends MongoRepository<MarketConfig, Integer> {

}