package com.fifaminer.repository;

import com.fifaminer.entity.Nation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationRepository extends MongoRepository<Nation, Long> {

}