package com.fifaminer.service.setting.model;

import com.fifaminer.service.setting.type.Setting;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "settings")
public class SettingConfiguration {

    @Id
    private final Setting setting;
    private final String value;
}
