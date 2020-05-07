package com.misernandfriends.cinemaclub.pojo.movie.crew;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Crew {

    @JsonProperty("credit_id")
    @SerializedName("credit_id")
    private String creditId;

    @JsonProperty("department")
    @SerializedName("department")
    private String department;

    @JsonProperty("gender")
    @SerializedName("gender")
    private Long gender;

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("job")
    @SerializedName("job")
    private String job;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("profile_path")
    @SerializedName("profile_path")
    private String profilePath;
}
