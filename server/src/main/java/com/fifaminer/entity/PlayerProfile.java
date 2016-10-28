package com.fifaminer.entity;

import com.fifaminer.entity.pojo.Attributes;
import com.fifaminer.entity.pojo.Items;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "player_profile")
public class PlayerProfile {

    @Id
    private Long id;

    public String commonName;

    public String firstName;

    public String headshotImgUrl;

    public String lastName;

    public Long leagueId;

    public Long nationId;

    public Long clubId;

    public String position;

    public String playerType;

    public List<Attributes> attributes;

    public String name;

    public String quality;

    public String color;

    public boolean isGK;

    public boolean isSpecialType;

    public Long baseId;

    public Integer rating;

    public Items fullInfo;

    private Date updated = new Date();
}