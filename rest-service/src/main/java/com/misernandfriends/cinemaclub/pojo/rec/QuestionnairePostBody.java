package com.misernandfriends.cinemaclub.pojo.rec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class QuestionnairePostBody {

    @JsonProperty("user_id")
    @SerializedName("user_id")
    private Long userId;

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
