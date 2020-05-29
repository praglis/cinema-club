package com.misernandfriends.cinemaclub.pojo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Badge {
    private Long id;
    private String name;
    private Integer valueFrom;
    private Integer valueTo;
}
