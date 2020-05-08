package com.misernandfriends.cinemaclub.pojo.movie.review.nyt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NYTLink {
    private String url;
    private String type;

    @JsonProperty("suggested_link_text")
    @SerializedName("suggested_link_text")
    private String suggestedText;
}