package com.fifaminer.entity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Attributes implements Serializable {

    @JsonProperty("name")
    public String name;

    @JsonProperty("value")
    public double value;

    @JsonProperty("chemistryBonus")
    public List<Object> chemistryBonus;
}
