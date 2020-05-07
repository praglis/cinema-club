package com.misernandfriends.cinemaclub.pojo.movie.review.nyt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NYTReview {
    private String byline;
    private String headline;
    private NYTLink link;
    private NYTMultimedia multimedia;

    @JsonProperty("critics_pick")
    @SerializedName("critics_pick")
    private Integer criticsPick;

    @JsonProperty("date_updated")
    @SerializedName("date_updated")
    private String modificationDate;

    @JsonProperty("display_title")
    @SerializedName("display_title")
    private String displayTitle;

    @JsonProperty("mppa_rating")
    @SerializedName("mppa_rating")
    private String rating;

    @JsonProperty("opening_date")
    @SerializedName("opening_date")
    private String openingDate;

    @JsonProperty("publication_date")
    @SerializedName("publication_date")
    private String publicationDate;

    @JsonProperty("summary_short")
    @SerializedName("summary_short")
    private String summaryShort;
}
