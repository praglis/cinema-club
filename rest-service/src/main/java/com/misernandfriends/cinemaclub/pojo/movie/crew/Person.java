package com.misernandfriends.cinemaclub.pojo.movie.crew;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Person {

    @JsonProperty("birthday")
    @SerializedName("birthday")
    private String birthday;

    @JsonProperty("known_for_department")
    @SerializedName("known_for_department")
    private String department;

    @JsonProperty("deathday")
    @SerializedName("deathday")
    private String deathday;

    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("also_known_as")
    @SerializedName("also_known_as")
    private List<String> knownAs;

    @JsonProperty("gender")
    @SerializedName("gender")
    private Integer gender;

    @JsonProperty("gender_name")
    @SerializedName("gender_name")
    private String genderName;

    @JsonProperty("biography")
    @SerializedName("biography")
    private String biography;

    @JsonProperty("popularity")
    @SerializedName("popularity")
    private Double popularity;

    @JsonProperty("place_of_birth")
    @SerializedName("place_of_birth")
    private String birthPlace;

    @JsonProperty("profile_path")
    @SerializedName("profile_path")
    private String pathProfile;

    @JsonProperty("adult")
    @SerializedName("adult")
    private Boolean adult;

    @JsonProperty("imdb_id")
    @SerializedName("imdb_id")
    private String imbdbId;

    @JsonProperty("homepage")
    @SerializedName("homepage")
    private String homepage;
}
