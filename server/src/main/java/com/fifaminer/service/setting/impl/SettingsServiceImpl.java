package com.fifaminer.service.setting.impl;

import com.fifaminer.repository.SettingRepository;
import com.fifaminer.service.setting.SettingsService;
import com.fifaminer.service.setting.model.SettingConfiguration;
import com.fifaminer.service.setting.type.Setting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingRepository settingRepository;

    private static final String DEFAULT = "Default";

    @Autowired
    public SettingsServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public String getSetting(Setting setting) {
        SettingConfiguration configuration = settingRepository.findOne(setting);
        return isNull(configuration) ? DEFAULT : configuration.getValue();
    }

    @Override
    public void updateSetting(SettingConfiguration settingConfiguration) {
        log.info("Update setting = {}", settingConfiguration);
        settingRepository.save(settingConfiguration);
    }
}
