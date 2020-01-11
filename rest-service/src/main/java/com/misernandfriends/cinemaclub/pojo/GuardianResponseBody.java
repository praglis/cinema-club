package com.misernandfriends.cinemaclub.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuardianResponseBody {
    @SerializedName("status")
    private String status;

    @SerializedName("userTier")
    private String userTier;

    @SerializedName("total")
    private Integer total;

    @SerializedName("startIndex")
    private Integer startIndex;

    @SerializedName("pageSize")
    private Integer pageSize;

    @SerializedName("currentPage")
    private Integer currentPage;

    @SerializedName("pages")
    private Integer pages;

    @SerializedName("orderBy")
    private String orderBy;

    @SerializedName("results")
    private List<GuardianResult> results;
}
