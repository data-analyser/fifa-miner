package com.fifaminer.service.setting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fifaminer.service.setting.type.Setting;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SettingConfigurationTO {

    @NotNull
    private final Setting setting;
    @NotNull
    @NotEmpty
    private final String value;

    @JsonCreator
    public SettingConfigurationTO(@JsonProperty("setting") Setting setting,
                                  @JsonProperty("value") String value) {
        this.setting = setting;
        this.value = value;
    }

    public Setting getSetting() {
        return setting;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingConfigurationTO that = (SettingConfigurationTO) o;

        if (setting != that.setting) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = setting != null ? setting.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
