package com.fifaminer.repository;

import com.fifaminer.entity.TransactionStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionAnalyseRepository extends MongoRepository<TransactionStatistics, Long> {
}
