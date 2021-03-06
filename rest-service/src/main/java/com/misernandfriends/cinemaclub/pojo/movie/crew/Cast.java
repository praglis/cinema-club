package com.misernandfriends.cinemaclub.pojo.movie.crew;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cast {

    @JsonProperty("cast_id")
    @SerializedName("cast_id")
    private Long castId;

    @JsonProperty("character")
    @SerializedName("character")
    private String character;

    @JsonProperty("credit_id")
    @SerializedName("credit_id")
    private String creditId;

    @JsonProperty("gender")
    @SerializedName("gender")
    private Long gender;

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("order")
    @SerializedName("order")
    private Long order;

    @JsonProperty("profile_path")
    @SerializedName("profile_path")
    private String profilePath;

    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    @JsonProperty("popularity")
    @SerializedName("popularity")
    private Double popularity;

    @JsonProperty("poster_path")
    @SerializedName("poster_path")
    private String posterPath;

}
