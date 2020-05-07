package com.misernandfriends.cinemaclub.pojo.user;

import com.misernandfriends.cinemaclub.pojo.movie.review.CommentReport;
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


    public UserReport(CommentReport commentReport) {
        this.reportDate = commentReport.getReportDate();
        this.reportReason = commentReport.getReportReason();
    }
}
