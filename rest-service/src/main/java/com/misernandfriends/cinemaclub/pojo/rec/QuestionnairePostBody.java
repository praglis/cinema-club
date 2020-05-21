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

    @JsonProperty("results")
    @SerializedName("results")
    private List<Movie> movies = new ArrayList<>();
}
