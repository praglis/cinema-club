package com.misernandfriends.cinemaclub.pojo.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    Integer Id;
    @JsonProperty("results")
    @SerializedName("results")
    List<VideoResults> results;
}
