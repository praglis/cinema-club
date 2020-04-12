package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.GenreCache;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.actor.ActorDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.movie.actor.ActorRepository;
import com.misernandfriends.cinemaclub.repository.user.RecommendationRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.RecommendationService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

    @Override
    public void processMovie(UserDTO user, MovieDTO movie) {
        JSONArray genres = new JSONObject(movieDetail.getMovieById(Integer.parseInt(movie.getApiUrl())))
                .getJSONArray("genres");
        for (int i = 0; i < genres.length(); i++) {
            String genreId = String.valueOf(genres.getJSONObject(i).getInt("id"));
            adjustRecommendation(user, genreId, RecommendationDTO.Type.Category);
        }
        JSONObject credits = new JSONObject(movieDetail.getMovieCreditsById(Integer.valueOf(movie.getApiUrl())));
        JSONArray cast = credits.getJSONArray("cast");
        JSONArray crew = credits.getJSONArray("crew");
        for (int i = 0; i < cast.length() && i < 5; i++) {
            JSONObject castObject = cast.getJSONObject(i);
            String castId = String.valueOf(castObject.getInt("id"));
            adjustRecommendation(user, castId, RecommendationDTO.Type.Actor);
            if (!actorRepository.getByUrlApi(castId).isPresent()) {
                actorRepository.create(ActorDTO.newInstance(castId, castObject.getString("name")));
            }
        }

        for (int i = 0; i < crew.length(); i++) {
            JSONObject crewObject = crew.getJSONObject(i);
            if (!"Directing".equals(crewObject.getString("department"))) {
                continue;
            }
            String crewId = String.valueOf(crewObject.getInt("id"));
            adjustRecommendation(user, crewId, RecommendationDTO.Type.Director);
            if (!actorRepository.getByUrlApi(crewId).isPresent()) {
                actorRepository.create(ActorDTO.newInstance(crewId, crewObject.getString("name")));
            }
        }
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
