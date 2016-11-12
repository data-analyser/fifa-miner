package com.fifaminer.converter;

import com.fifaminer.service.setting.model.SettingConfiguration;
import com.fifaminer.client.dto.SettingConfigurationTO;
import com.fifaminer.service.setting.type.Setting;
import org.springframework.stereotype.Component;

@Component
public class SettingConfigurationConverter {

    public SettingConfiguration fromTO(SettingConfigurationTO settingConfigurationTO) {
        return new SettingConfiguration(
                Setting.valueOf(settingConfigurationTO.getSettingTO().name()),
                settingConfigurationTO.getValue()
        );
    }
}
