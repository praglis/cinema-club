package com.misernandfriends.cinemaclub.pojo.movie.review.guardian;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GuardianResult {
    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("sectionId")
    private String sectionId;

    @SerializedName("sectionName")
    private String sectionName;

    @SerializedName("webPublicationDate")
    private Date webPublicationDate;

    @SerializedName("webTitle")
    private String webTitle;

    @SerializedName("webUrl")
    private String webUrl;

    @SerializedName("apiUrl")
    private String apiUrl;

    @SerializedName("isHosted")
    private Boolean isHosted;

    @SerializedName("pillarId")
    private String pillarId;

    @SerializedName("pillarName")
    private String pillarName;

    @SerializedName("fields")
    private FieldsResponse fields;
}
