package com.fifaminer.converter;

import com.fifaminer.service.setting.model.SettingConfiguration;
import com.fifaminer.service.setting.model.SettingConfigurationTO;
import org.springframework.stereotype.Component;

@Component
public class SettingConfigurationConverter {

    public SettingConfiguration fromTO(SettingConfigurationTO settingConfigurationTO) {
        return new SettingConfiguration(
                settingConfigurationTO.getSetting(),
                settingConfigurationTO.getValue()
        );
    }
}
