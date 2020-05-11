package com.misernandfriends.cinemaclub.pojo.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @JsonProperty("Id")
    @SerializedName("Id")
    Integer Id;
    @JsonProperty("results")
    @SerializedName("results")
    List<VideoResults> results;
}
