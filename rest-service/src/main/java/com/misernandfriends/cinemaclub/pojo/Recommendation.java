package com.misernandfriends.cinemaclub.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    private List<String> recommendation_values;

    private MoviesList movies;

}
