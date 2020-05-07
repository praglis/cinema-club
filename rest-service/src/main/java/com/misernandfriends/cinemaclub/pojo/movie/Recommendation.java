package com.misernandfriends.cinemaclub.pojo.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("recommendation_values")
    private List<String> recomVariable;

    @JsonProperty("movies")
    private MoviesList movies;

    @JsonProperty("recommendations")
    private Boolean recommendationsPresent;

}
