package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends AbstractRepository<FavouriteDTO> {
    List<FavouriteDTO> getUserFavourites(Long userId);
    List<FavouriteDTO> findAll();
    Optional<FavouriteDTO> getByUrl(Long userId, String apiUrl);
    void deleteFavourite(Long userId, String movieUrl);
}
