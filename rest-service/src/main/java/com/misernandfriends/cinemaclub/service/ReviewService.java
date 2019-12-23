package com.misernandfriends.cinemaclub.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misernandfriends.cinemaclub.pojo.GuardianResponse;
import com.misernandfriends.cinemaclub.pojo.GuardianResult;
import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ReviewService implements ReviewServiceLocal {
    @Override
    public String getNYTCriticsPicksReview() {
        final String uri = "https://api.nytimes.com/svc/movies/v2/reviews/picks.json?api-key=iDAhRto0lVh4h1FAhnLiYTHjHePGWVLL";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getNYTMovieReview(String title) {
        final String uri = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?query=" + title + "&api-key=iDAhRto0lVh4h1FAhnLiYTHjHePGWVLL";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getGuardianMovieReview(String title) {
        ObjectMapper m = new ObjectMapper();

        String uri = "https://content.guardianapis.com/search?api-key=5ecfbb08-6408-46ca-aa33-60985a0f1f83&q=" + title + " review";
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(uri, String.class);

        GuardianResponse allMatching;
        try {
            allMatching = m.readValue(json, new TypeReference<GuardianResponse>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        if (allMatching == null || allMatching.getResponse().getResults() == null) {
            return "";
        }

        Optional<GuardianResult> optional = allMatching.getResponse().getResults().stream().filter(e -> e.getWebTitle().contains(title + " review ")).findFirst();

        if (optional.isPresent()) {
            uri = optional.get().getApiUrl() + "?api-key=5ecfbb08-6408-46ca-aa33-60985a0f1f83&show-fields=byline,trailText";
            return restTemplate.getForObject(uri, String.class);
        }

        return "";
    }
}