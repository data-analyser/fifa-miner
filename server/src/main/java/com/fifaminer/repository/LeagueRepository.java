package com.fifaminer.repository;

import com.fifaminer.entity.League;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends MongoRepository<League, Long> {

}