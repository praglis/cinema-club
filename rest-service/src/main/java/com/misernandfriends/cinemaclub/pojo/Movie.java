package com.misernandfriends.cinemaclub.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie {
    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("adult")
    private Boolean adult;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("title")
    private String title;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("video")
    private Boolean hasVideo;

    @JsonProperty("vote_average")
    private Double averageVote;
}
