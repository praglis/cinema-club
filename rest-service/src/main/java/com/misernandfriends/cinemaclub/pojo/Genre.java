package com.misernandfriends.cinemaclub.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Genre {
    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;
}
