package com.misernandfriends.cinemaclub.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuardianResponse {
    @SerializedName("response")
    private GuardianResponseBody response;
}
