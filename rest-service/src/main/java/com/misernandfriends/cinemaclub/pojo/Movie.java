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
    @SerializedName("poster_path")
    private String posterPath;

    @JsonProperty("adult")
    @SerializedName("adult")
    private Boolean adult;

    @JsonProperty("overview")
    @SerializedName("overview")
    private String overview;

    @JsonProperty("release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @JsonProperty("genre_ids")
    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @JsonProperty("id")
    @SerializedName("id")
    private Integer id;

    @JsonProperty("original_title")
    @SerializedName("original_title")
    private String originalTitle;

    @JsonProperty("original_language")
    @SerializedName("original_language")
    private String originalLanguage;

    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    @JsonProperty("backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    @JsonProperty("popularity")
    @SerializedName("popularity")
    private Double popularity;

    @JsonProperty("vote_count")
    @SerializedName("vote_count")
    private Integer voteCount;

    @JsonProperty("video")
    @SerializedName("video")
    private Boolean hasVideo;

    @JsonProperty("vote_average")
    @SerializedName("vote_average")
    private Double averageVote;

    @JsonProperty("rating")
    @SerializedName("rating")
    private Double rating;

    @JsonProperty("numberOfVotes")
    @SerializedName("numberOfVotes")
    private Long numberOfVotes;
}
