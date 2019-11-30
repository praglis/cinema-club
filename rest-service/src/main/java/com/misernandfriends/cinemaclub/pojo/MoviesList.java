package com.misernandfriends.cinemaclub.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MoviesList {
    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<Movie> movies;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;
}