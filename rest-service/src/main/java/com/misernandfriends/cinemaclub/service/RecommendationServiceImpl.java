package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.GenreCache;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.actor.ActorDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.pojo.Cast;
import com.misernandfriends.cinemaclub.pojo.Credits;
import com.misernandfriends.cinemaclub.pojo.Crew;
import com.misernandfriends.cinemaclub.repository.movie.actor.ActorRepository;
import com.misernandfriends.cinemaclub.repository.user.RecommendationRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRatingRepository;
import com.misernandfriends.cinemaclub.serviceInterface.FavouriteService;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.RecommendationService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private static Double START_FIT_LEVEL = 20d;
    private static Double CHANGE_VALUE = 50d;

    @Autowired
    private MovieServiceLocal movieService;

    @Autowired
    private MovieFetchServiceLocal movieDetail;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Override
    public void processMovie(UserDTO user, MovieDTO movie) {
        JSONArray genres = new JSONObject(movieDetail.getMovieById(Integer.parseInt(movie.getApiUrl())))
                .getJSONArray("genres");
        for (int i = 0; i < genres.length(); i++) {
            String genreId = String.valueOf(genres.getJSONObject(i).getInt("id"));
            adjustRecommendation(user, genreId, RecommendationDTO.Type.Category);
        }
        Credits credits = movieDetail.getMovieCreditsById(Integer.valueOf(movie.getApiUrl()));
        List<Cast> cast = credits.getCast();
        List<Crew> crew = credits.getCrew();
        for (int i = 0; i < cast.size() && i < 5; i++) {
            Cast castObject = cast.get(i);
            String castId = String.valueOf(castObject.getId());
            adjustRecommendation(user, castId, RecommendationDTO.Type.Actor);
            if (!actorRepository.getByUrlApi(castId).isPresent()) {
                actorRepository.create(ActorDTO.newInstance(castId, castObject.getName()));
            }
        }

        for (int i = 0; i < crew.size(); i++) {
            Crew crewObject = crew.get(i);
            if (!"Directing".equals(crewObject.getDepartment())) {
                continue;
            }
            String crewId = String.valueOf(crewObject.getId());
            adjustRecommendation(user, crewId, RecommendationDTO.Type.Director);
            if (!actorRepository.getByUrlApi(crewId).isPresent()) {
                actorRepository.create(ActorDTO.newInstance(crewId, crewObject.getName()));
            }
        }
    }

    @Override
    public void getMovieBaseOnTaste(UserDTO user) {
        int maxResults = 100;

        List<FavouriteDTO> favourites = favouriteRepository.getUserFavourites(user.getId(), DateTimeUtil.minusDate(DateTimeUtil.getCurrentDate(), 1, Calendar.MONTH), maxResults);
        ;
        List<String> moviesUrls = favourites.stream().map(favouriteDTO -> favouriteDTO.getMovie().getApiUrl()).collect(Collectors.toList());
        if (moviesUrls.size() < 100) {
            List<UserRatingDTO> rating = userRatingRepository.getUserBestRatedMovies(user.getId(), maxResults - moviesUrls.size());
            moviesUrls.addAll(rating.stream().map(userRatingDTO -> userRatingDTO.getMovie().getApiUrl()).collect(Collectors.toList()));
        }
        List<Long> similarTaste = recommendationRepository.getSimilarUser(moviesUrls, user.getId());

    }

    @Override
    @Async
    public void processMovieAsync(UserDTO user, MovieDTO movie) {
        processMovie(user, movie);
    }

    @Override
    public List<RecommendationDTO> getRecommendation(UserDTO user, String type, int maxResult) {
        return recommendationRepository.getRecommendation(user.getId(), type, maxResult);
    }

    @Override
    public List<String> getValues(UserDTO userDTO, String type) {
        List<RecommendationDTO> recommendations = recommendationRepository.getRecommendation(userDTO.getId(), type, 5);
        if (RecommendationDTO.Type.Category.equals(type)) {
            return recommendations.stream()
                    .map(recom -> GenreCache.get(Integer.parseInt(recom.getValue())))
                    .collect(Collectors.toList());
        } else {
            return recommendations.stream()
                    .map(recom -> actorRepository.getNameByUrlApi(recom.getValue()))
                    .collect(Collectors.toList());
        }
    }

    private void adjustRecommendation(UserDTO user, String castId, String actor) {
        RecommendationDTO recommendation = recommendationRepository.get(user.getId(), actor, castId);
        if (recommendation == null) {
            recommendation = new RecommendationDTO();
            recommendation.setType(actor);
            recommendation.setUserId(user.getId());
            recommendation.setValue(castId);
            recommendation.setFitLevel(Math.log(START_FIT_LEVEL));
        }
        Double value = Math.log(Math.exp(recommendation.getFitLevel()) + CHANGE_VALUE);
        recommendation.setFitLevel(value);

        if (recommendation.getId() != null) {
            recommendationRepository.update(recommendation);
        } else {
            recommendationRepository.create(recommendation);
        }
    }
}
