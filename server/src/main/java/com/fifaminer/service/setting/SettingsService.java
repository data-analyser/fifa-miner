package com.fifaminer.service.setting;

import com.fifaminer.service.setting.model.SettingConfiguration;

public interface SettingsService {

    void updateSetting(SettingConfiguration settingConfiguration);

    String getSetting(String settingName);
}
