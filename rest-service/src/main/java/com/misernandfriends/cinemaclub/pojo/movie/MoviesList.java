package com.misernandfriends.cinemaclub.pojo.movie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoviesList {
    @JsonProperty("page")
    @SerializedName("page")
    private Integer page;

    @JsonProperty("results")
    @SerializedName("results")
    private List<Movie> movies = new ArrayList<>();

    @JsonProperty("total_results")
    @SerializedName("total_results")
    private Integer totalResults;

    @JsonProperty("total_pages")
    @SerializedName("total_pages")
    private Integer totalPages;
}