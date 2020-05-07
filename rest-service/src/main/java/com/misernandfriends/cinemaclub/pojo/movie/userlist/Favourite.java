package com.misernandfriends.cinemaclub.pojo.movie.userlist;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Favourite {
    @SerializedName("user_id")
    private Long userId;

    @SerializedName("movie_url")
    private String movieUrl;

    @SerializedName("movie_title")
    private String movieTitle;
}
