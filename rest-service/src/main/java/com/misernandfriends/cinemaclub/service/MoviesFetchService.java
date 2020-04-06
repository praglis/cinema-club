package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.model.user.FavoriteMovieDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.Movie;
import com.misernandfriends.cinemaclub.pojo.MoviesList;
import com.misernandfriends.cinemaclub.serviceInterface.FavouriteService;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.MoviesFetchServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.RecommendationService;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoviesFetchService implements MoviesFetchServiceLocal {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private MovieServiceLocal movieServiceLocal;

    @Autowired
    private FavouriteService favouriteService;

    @Override
    public String getBestRatedMovies(Integer page) {
        page = (page == null || page <= 0) ? 1 : page;

        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_TOP_RATED).addQuery("page", page.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getPopularMovies(Integer page) {
        page = (page == null || page <= 0) ? 1 : page;
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_API_URL_MOST_POPULAR).addQuery("page", page.toString()).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public MoviesList getRecommendedMovies(UserDTO user, Integer page, String type) {
        List<Long> favoriteMovies = favouriteService.getUserFavourites(user.getId())
                .stream().map(favouriteDTO -> Long.parseLong(favouriteDTO.getMovie().getApiUrl())).collect(Collectors.toList());
        String criteriaParamName = RecommendationDTO.Type.getQueryParameter(type);
        page = (page == null || page <= 0) ? 1 : page;
        List<RecommendationDTO> list = recommendationService.getRecommendation(user, type, 3);
        String uri = new UrlHelper(CacheValue._API_URLS.MOVIES_DISCOVER_API_URL)
                .addQuery(criteriaParamName, getRecommendationValueAsString(list))
                .build();
        return getRecommendedMovies(null, page, uri, favoriteMovies);
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
            reduceRecommendationList(currentList, 20);
        }
        if (currentList.getMovies().size() < 20) {
            getRecommendedMovies(currentList, ++page, uri, favoriteMovies);
        }
        return currentList;
    }

    private void reduceRecommendationList(MoviesList currentList, int maxSize) {
        if (currentList.getMovies().size() < maxSize) {
            return;
        }
        Iterator<Movie> iterator = currentList.getMovies().iterator();
        int index = -1;
        while (iterator.hasNext()) {
            index++;
            iterator.next();
            if (index < maxSize) {
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
