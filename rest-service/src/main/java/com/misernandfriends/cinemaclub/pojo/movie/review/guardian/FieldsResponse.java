package com.misernandfriends.cinemaclub.pojo.movie.review.guardian;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldsResponse {
    @SerializedName("trailText")
    private String trailText;

    @SerializedName("byline")
    private String byline;
}
