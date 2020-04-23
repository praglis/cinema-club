package com.misernandfriends.cinemaclub.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieSearchCriteria {
    private Long genre;
    private Integer yearFrom;
    private Integer yearTo;
    private Double voteFrom;
    private Double voteTo;
    private Integer page = 1;
}
