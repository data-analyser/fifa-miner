package com.fifaminer.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class SettingConfigurationTO {

    @NotNull
    private final String settingName;
    @NotNull
    private final String value;

    @JsonCreator
    public SettingConfigurationTO(@JsonProperty("settingName") String settingName,
                                  @JsonProperty("value") String value) {
        this.settingName = settingName;
        this.value = value;
    }

    public String getSettingName() {
        return settingName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingConfigurationTO that = (SettingConfigurationTO) o;

        if (settingName != null ? !settingName.equals(that.settingName) : that.settingName != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = settingName != null ? settingName.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
