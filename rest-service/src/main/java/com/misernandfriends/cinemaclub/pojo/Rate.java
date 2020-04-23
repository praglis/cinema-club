package com.misernandfriends.cinemaclub.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rate {

    @JsonProperty("rate")
    @SerializedName("rate")
    private Integer rate;

}
