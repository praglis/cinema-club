package com.misernandfriends.cinemaclub.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class MovieHelper {

    private String movieTitle;
    private Date premiereDate;

}
