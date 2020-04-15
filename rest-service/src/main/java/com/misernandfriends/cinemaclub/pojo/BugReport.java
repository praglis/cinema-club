package com.misernandfriends.cinemaclub.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BugReport {
    @SerializedName("reporter")
    private String reporterUsername;

    @SerializedName("reportDate")
    private Date reportDate;

    @SerializedName("bugDescription")
    private String bugDescription;
}
