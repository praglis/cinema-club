package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.model.movie.PlanToWatchMovieDTO;

import java.util.List;

public interface PersonalListService {
    List<FavouriteDTO> getUserFavourites(Long id);
    List<PlanToWatchMovieDTO> getUserPlanToWatch(Long id);
    void createFavourite(Long userId, String title, String url) throws ApplicationException;
    void createPlanToWatch(Long userId, String title, String url) throws ApplicationException;
    void deleteFavourite(Long userId, String url);
    void deletePlanToWatch(Long userId, String url);
}
