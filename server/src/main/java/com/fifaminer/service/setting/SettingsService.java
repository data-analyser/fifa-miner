package com.fifaminer.service.setting;

import com.fifaminer.service.setting.model.SettingConfiguration;
import com.fifaminer.service.setting.type.Setting;

public interface SettingsService {

    String getSetting(Setting setting);

    void updateSetting(SettingConfiguration settingConfiguration);
}
