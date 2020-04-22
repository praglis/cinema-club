package com.misernandfriends.cinemaclub.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentReport {
    @SerializedName("commentId")
    private String reporterUsername;

    @SerializedName("reportDate")
    private Date reportDate;

    @SerializedName("reportReason")
    private String bugDescription;
}
