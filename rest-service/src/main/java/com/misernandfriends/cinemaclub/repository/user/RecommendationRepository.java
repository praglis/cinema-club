package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface RecommendationRepository extends AbstractRepository<RecommendationDTO> {

    RecommendationDTO get(Long userId, String type, String value);

    List<RecommendationDTO> getByUser(Long userId, String type);

    List<RecommendationDTO> getRecommendation(Long userId, String type, int maxResult);
}
