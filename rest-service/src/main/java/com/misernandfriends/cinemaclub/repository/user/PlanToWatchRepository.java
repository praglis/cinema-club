package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.movie.PlanToWatchMovieDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface PlanToWatchRepository extends AbstractRepository<PlanToWatchMovieDTO> {
    List<PlanToWatchMovieDTO> getUserPlanToWatch(Long userId);
    List<PlanToWatchMovieDTO> findAll();
    Optional<PlanToWatchMovieDTO> getByUrl(Long userId, String apiUrl);
    void deletePlanToWatch(Long userId, String movieUrl);
}
