package com.misernandfriends.cinemaclub.service.movie;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.pojo.rec.QuestionnaireMovieResponse;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieDetailService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MoviesFetchService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.PersonalListService;
import com.misernandfriends.cinemaclub.serviceInterface.rec.QuestionnaireMovieService;
import com.misernandfriends.cinemaclub.serviceInterface.rec.RecommendationService;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoviesFetchServiceImpl implements MoviesFetchService {

    private static final Integer MAX_MOVIES_IN_RECOMMENDATION_LIST = 20;

    private final RecommendationService recommendationService;
    private final QuestionnaireMovieService questionnaireMovieService;
    private final PersonalListService personalListService;
    private final MovieDetailService movieFetchServiceLocal;

    public MoviesFetchServiceImpl(RecommendationService recommendationService, QuestionnaireMovieService questionnaireMovieService, PersonalListService personalListService, MovieDetailService movieFetchServiceLocal) {
        this.recommendationService = recommendationService;
        this.questionnaireMovieService = questionnaireMovieService;
        this.personalListService = personalListService;
        this.movieFetchServiceLocal = movieFetchServiceLocal;
    }

    @Override
    public MoviesList getBestRatedMovies(Integer page) {
        page = (page == null || page <= 0) ? 1 : page;

        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_TOP_RATED).addQuery("page", page.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, MoviesList.class);
    }

    @Override
    public MoviesList getPopularMovies(Integer page) {
        page = (page == null || page <= 0) ? 1 : page;
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_MOST_POPULAR).addQuery("page", page.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, MoviesList.class);
    }

    @Override
    public MoviesList getRecommendedMovies(UserDTO user, Integer page, String type) {
        if (RecommendationDTO.Type.Similar.equals(type)) {
            int pagePerRequest = 20;
            if (page == null || page == 0) {
                page = 1;
            }
            List<MovieDTO> movieBaseOnTaste = recommendationService.getMovieBaseOnTaste(user);
            MoviesList moviesList = new MoviesList();
            moviesList.setPage(page);
            moviesList.setTotalPages(movieBaseOnTaste.size());
            moviesList.setTotalResults(movieBaseOnTaste.size());
            for (int i = page * pagePerRequest - pagePerRequest; i < movieBaseOnTaste.size() && i < page * pagePerRequest; i++) {
                moviesList.getMovies().add(movieFetchServiceLocal.getMovieById(Integer.parseInt(movieBaseOnTaste.get(i).getApiUrl())));
            }
            return moviesList;
        }

        List<Long> favoriteMovies = personalListService.getUserFavourites(user.getId())
                .stream().map(favouriteDTO -> Long.parseLong(favouriteDTO.getMovie().getApiUrl()))
                .collect(Collectors.toList());

        String criteriaParamName = RecommendationDTO.Type.getQueryParameter(type);
        page = (page == null || page <= 0) ? 1 : page;
        List<RecommendationDTO> list = recommendationService.getRecommendation(user, type, 3);
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_DISCOVER_API_URL)
                .addQuery(criteriaParamName, getRecommendationValueAsString(list))
                .build();

        return getRecommendedMovies(null, page, uri, favoriteMovies);
    }

    @Override
    public QuestionnaireMovieResponse getQuestionnaireMovies() {
        return questionnaireMovieService.getQuestionnaireMovies();
    }

    private MoviesList getRecommendedMovies(MoviesList currentList, int page, String uri, List<Long> favoriteMovies) {
        MoviesList result = new RestTemplate().getForObject(new UrlHelper(uri).addQuery("page", String.valueOf(page)).build(), MoviesList.class);
        if (result == null) {
            return null;
        }

        removeSeenMovies(result, favoriteMovies);
        if (currentList == null) {
            currentList = result;
        } else {
            currentList.getMovies().addAll(result.getMovies());
            reduceRecommendationList(currentList);
        }

        if (currentList.getMovies().size() < 20 && !currentList.getTotalPages().equals(page)) {
            getRecommendedMovies(currentList, ++page, uri, favoriteMovies);
        }

        return currentList;
    }

    private void reduceRecommendationList(MoviesList currentList) {
        if (currentList.getMovies().size() < MAX_MOVIES_IN_RECOMMENDATION_LIST) {
            return;
        }

        Iterator<Movie> iterator = currentList.getMovies().iterator();
        int index = -1;
        while (iterator.hasNext()) {
            index++;
            iterator.next();
            if (index < MAX_MOVIES_IN_RECOMMENDATION_LIST) {
                continue;
            }
            iterator.remove();
        }
    }

    private void removeSeenMovies(MoviesList movies, List<Long> favoriteMovies) {
        movies.getMovies().removeIf(movie -> favoriteMovies.contains(Integer.toUnsignedLong(movie.getId())));
    }

    private String getRecommendationValueAsString(List<RecommendationDTO> list) {
        if (list.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder(list.get(0).getValue());
        for (int i = 1; i < list.size(); i++) {
            builder.append("|").append(list.get(i).getValue());
        }

        return builder.toString();
    }
}
