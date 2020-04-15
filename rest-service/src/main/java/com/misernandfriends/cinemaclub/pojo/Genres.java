package com.misernandfriends.cinemaclub.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Genres {
    @JsonProperty("genres")
    @SerializedName("genres")
    private List<Genre> genres;
}
