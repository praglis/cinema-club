package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReviewService implements ReviewServiceLocal {
    @Override
    public String getNYTCriticsPicksReview() {
        final String uri = "https://api.nytimes.com/svc/movies/v2/reviews/picks.json?api-key=iDAhRto0lVh4h1FAhnLiYTHjHePGWVLL";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}