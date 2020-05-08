package com.misernandfriends.cinemaclub.pojo.movie.review;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentReport {
    @SerializedName("commentId")
    private String commentId;

    @SerializedName("reportDate")
    private Date reportDate;

    @SerializedName("reportReason")
    private String reportReason;
}
