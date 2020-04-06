package com.misernandfriends.cinemaclub.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MoviesList {
    @JsonProperty("page")
    private Integer page;

    @JsonProperty("results")
    private List<Movie> movies = new ArrayList<>();

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("total_pages")
    private Integer totalPages;
}