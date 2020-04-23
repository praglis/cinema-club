package com.misernandfriends.cinemaclub.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Credits {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("cast")
    @SerializedName("cast")
    private List<Cast> cast;

    @JsonProperty("crew")
    @SerializedName("crew")
    private List<Crew> crew;
}
