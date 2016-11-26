package com.fifaminer.repository;

import com.fifaminer.entity.PlayerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerProfileRepository extends MongoRepository<PlayerProfile, Long> {

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<PlayerProfile> findByName(String phrase);

    List<PlayerProfile> findByLeagueId(Long leagueId);
}