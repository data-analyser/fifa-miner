package com.fifaminer.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class SettingConfigurationTO {

    @NotNull
    private final SettingTO settingTO;
    @NotNull
    private final String value;

    @JsonCreator
    public SettingConfigurationTO(@JsonProperty("settingTO") SettingTO settingTO,
                                  @JsonProperty("value") String value) {
        this.settingTO = settingTO;
        this.value = value;
    }

    public SettingTO getSettingTO() {
        return settingTO;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingConfigurationTO that = (SettingConfigurationTO) o;

        if (settingTO != that.settingTO) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = settingTO != null ? settingTO.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
