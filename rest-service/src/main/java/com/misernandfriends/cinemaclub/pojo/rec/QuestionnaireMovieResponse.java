package com.misernandfriends.cinemaclub.pojo.rec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class QuestionnaireMovieResponse {


    public QuestionnaireMovieResponse() {

    }

    @JsonProperty("results")
    private List<Movie> movies;

    @JsonProperty("total_results")
    private Integer totalResults;

    public QuestionnaireMovieResponse getResponse(MoviesList popularResponse, MoviesList topRatedResponse) {
        List<Movie> resultList = popularResponse.getMovies().stream().filter( e-> e.getPosterPath() != null).limit(8).collect(Collectors.toList());
        resultList.addAll(topRatedResponse.getMovies().stream().filter( e-> (e.getPosterPath() != null)).limit(8).collect(Collectors.toList()));
        this.movies = resultList;
        this.totalResults = resultList.size();
        return this;
    }
}
