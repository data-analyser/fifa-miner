package com.fifaminer.service.setting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "settings")
public class SettingConfiguration {

    @Id
    private final String settingName;
    private final String value;
}
