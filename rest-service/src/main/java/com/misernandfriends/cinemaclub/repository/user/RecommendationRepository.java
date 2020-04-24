package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RecommendationRepository extends AbstractRepository<RecommendationDTO> {

    RecommendationDTO get(Long userId, String type, String value);

    List<RecommendationDTO> getByUser(Long userId, String type);

    List<RecommendationDTO> getRecommendation(Long userId, String type, int maxResult);

    Map<Long, Integer> getSimilarUser(List<String> moviesUrl, Long exceptUserId);

    List<MovieDTO> findBestMoviesForBy(Long id, Long userId, int moviesToAdd);
}
