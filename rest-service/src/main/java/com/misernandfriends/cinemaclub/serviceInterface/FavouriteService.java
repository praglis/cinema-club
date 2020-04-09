package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;

import java.util.List;

public interface FavouriteService {
    List<FavouriteDTO> getUserFavourites(Long id);
    void createFavourite(Long userId, String title, String url);
    void deleteFavourite(Long userId, String url);
}
