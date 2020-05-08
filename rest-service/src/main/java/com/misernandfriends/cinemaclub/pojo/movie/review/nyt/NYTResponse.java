package com.misernandfriends.cinemaclub.pojo.movie.review.nyt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NYTResponse {
    private String copyright;
    private NYTReview [] results;
    private String status;

    @JsonProperty("has_more")
    @SerializedName("has_more")
    private Boolean hasMore;

    @JsonProperty("num_results")
    @SerializedName("num_results")
    private Integer resultsSize;
}
