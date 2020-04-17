package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.pojo.MoviesList;
import com.misernandfriends.cinemaclub.pojo.QuestionnaireMovieResponse;
import com.misernandfriends.cinemaclub.serviceInterface.QuestionnaireMovieService;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class QuestionnaireMoviesServiceImpl implements QuestionnaireMovieService {

    @Override
    public QuestionnaireMovieResponse getQuestionnaireMovies() {
        Random r = new Random();
        int page1 = r.nextInt((10) + 1);
        int page2 = r.nextInt((10) + 1);
        String uriPopular = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_MOST_POPULAR).addQuery("page", Integer.toString(page2)).build();
        String uriTopRated = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_TOP_RATED).addQuery("page", Integer.toString(page1)).build();
        MoviesList popularResponse = new RestTemplate().getForObject(uriPopular, MoviesList.class);
        MoviesList topRatedResponse = new RestTemplate().getForObject(uriTopRated, MoviesList.class);
        QuestionnaireMovieResponse questionnaireMoviesResponse = new QuestionnaireMovieResponse();
        return questionnaireMoviesResponse.getResponse(popularResponse, topRatedResponse);
    }
}
