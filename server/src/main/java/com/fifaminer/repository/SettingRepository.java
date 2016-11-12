package com.fifaminer.repository;

import com.fifaminer.service.setting.model.SettingConfiguration;
import com.fifaminer.service.setting.type.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends MongoRepository<SettingConfiguration, Setting> {
}
