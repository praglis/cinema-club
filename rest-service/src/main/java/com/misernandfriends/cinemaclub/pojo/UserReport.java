package com.misernandfriends.cinemaclub.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserReport {

    private String reportingUsername;
    private String reportedUsername;
    private String reportedComment;
    private Date reportDate;
    private String reportReason;
}
