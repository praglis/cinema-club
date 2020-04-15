package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.repository.user.FavouriteRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.FavouriteService;
import com.misernandfriends.cinemaclub.serviceInterface.RecommendationService;
import com.misernandfriends.cinemaclub.serviceInterface.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RecommendationService recommendationService;

    @Override
    public List<FavouriteDTO> getUserFavourites(Long id) {
        return favouriteRepository.getUserFavourites(id);
    }

    @Override
    @Transactional
    public void createFavourite(Long userId, String title, String url) {
        Optional<MovieDTO> existingMovie = movieRepository.getByApiUrl(url);
        Optional<UserDTO> existingUser = userRepository.getById(userId);

        if (!existingUser.isPresent()) {
            return;
        }

        if (!existingUser.get().getUsername().equals(securityService.findLoggedInUsername())) {
            return;
        }

        MovieDTO movie;
        if (!existingMovie.isPresent()) {
            movie = new MovieDTO();
            movie.setApiUrl(url);
            movie.setTitle(title);
            movie = movieRepository.create(movie);
        } else {
            movie = existingMovie.get();
        }

        FavouriteDTO favouriteDTO = new FavouriteDTO();
        favouriteDTO.setMovie(movie);
        favouriteDTO.setUser(existingUser.get());
        favouriteRepository.create(favouriteDTO);
        recommendationService.processMovieAsync(existingUser.get(), movie);
    }

    @Override
    @Transactional
    public void deleteFavourite(Long userId, String url) {
        Optional<UserDTO> existingUser = userRepository.getById(userId);

        if (!existingUser.isPresent()) return;

        Optional<FavouriteDTO> favouriteDTO = favouriteRepository.getByUrl(userId, url);
        if (favouriteDTO.isPresent()) {
            favouriteRepository.deleteFavourite(userId, url);
        }
    }
}
