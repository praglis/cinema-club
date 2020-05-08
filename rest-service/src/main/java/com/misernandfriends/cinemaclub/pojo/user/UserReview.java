package com.misernandfriends.cinemaclub.pojo.user;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReview {

    @SerializedName("movieId")
    private Integer movieId;

    @SerializedName("cinemaId")
    private Long cinemaId;

    @SerializedName("movieTitle")
    private String movieTitle;

    @SerializedName("reviewBody")
    private String reviewBody;

    @SerializedName("reviewId")
    private Long reviewId;

    @SerializedName("parentReviewId")
    private Long parentReviewId;
}
