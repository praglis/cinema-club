package com.misernandfriends.cinemaclub.service.movie;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.PlanToWatchMovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.repository.user.FavouriteRepository;
import com.misernandfriends.cinemaclub.repository.user.PlanToWatchRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.movie.PersonalListService;
import com.misernandfriends.cinemaclub.serviceInterface.rec.RecommendationService;
import com.misernandfriends.cinemaclub.serviceInterface.config.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonalListServiceImpl implements PersonalListService {

    private final FavouriteRepository favouriteRepository;
    private final PlanToWatchRepository planToWatchRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final RecommendationService recommendationService;

    public PersonalListServiceImpl(FavouriteRepository favouriteRepository, PlanToWatchRepository planToWatchRepository,
                                   MovieRepository movieRepository, UserRepository userRepository,
                                   SecurityService securityService, RecommendationService recommendationService) {
        this.favouriteRepository = favouriteRepository;
        this.planToWatchRepository = planToWatchRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.recommendationService = recommendationService;
    }

    @Override
    public List<FavouriteDTO> getUserFavourites(Long id) {
        return favouriteRepository.getUserFavourites(id);
    }

    @Override
    @Transactional
    public void createFavourite(Long userId, String title, String url) throws ApplicationException {
        Optional<UserDTO> existingUser = userRepository.getById(userId);
        if (!existingUser.isPresent()) {
            log.error("User does not exist");
            throw new ApplicationException("User does not exist");
        }

        MovieDTO movie = startMovieProcess(url, existingUser.get(), title);
        FavouriteDTO favourite = new FavouriteDTO();
        favourite.setMovie(movie);
        favourite.setUser(existingUser.get());

        favouriteRepository.create(favourite);
        recommendationService.processMovieAsync(existingUser.get(), movie);
    }

    @Override
    @Transactional
    public void deleteFavourite(Long userId, String url) {
        Optional<UserDTO> existingUser = userRepository.getById(userId);

        if (!existingUser.isPresent()) {
            return;
        }

        Optional<FavouriteDTO> favouriteDTO = favouriteRepository.getByUrl(userId, url);
        if (favouriteDTO.isPresent()) {
            favouriteRepository.deleteFavourite(userId, url);
        }
    }

    @Override
    public List<PlanToWatchMovieDTO> getUserPlanToWatch(Long id) {
        return planToWatchRepository.getUserPlanToWatch(id);
    }

    @Override
    @Transactional
    public void createPlanToWatch(Long userId, String title, String url) throws ApplicationException {
        Optional<UserDTO> existingUser = userRepository.getById(userId);
        if (!existingUser.isPresent()) {
            log.error("User does not exist");
            throw new ApplicationException("User does not exist");
        }

        MovieDTO movie = startMovieProcess(url, existingUser.get(), title);
        PlanToWatchMovieDTO planToWatchMovieDTO = new PlanToWatchMovieDTO();
        planToWatchMovieDTO.setMovie(movie);
        planToWatchMovieDTO.setUser(existingUser.get());

        planToWatchRepository.create(planToWatchMovieDTO);
    }

    private MovieDTO startMovieProcess(String url, UserDTO existingUser, String title) throws ApplicationException {
        Optional<MovieDTO> existingMovie = movieRepository.getByApiUrl(url);

        if (!existingUser.getUsername().equals(securityService.findLoggedInUsername())) {
            log.error("User does not have permission");
            throw new ApplicationException("User does not have permission");
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

        return movie;
    }

    @Override
    @Transactional
    public void deletePlanToWatch(Long userId, String url) {
        Optional<UserDTO> existingUser = userRepository.getById(userId);

        if (!existingUser.isPresent()) {
            log.error("User does not exist");
            throw new ApplicationException("User does not exist");
        }

        Optional<PlanToWatchMovieDTO> planToWatchMovieDTO = planToWatchRepository.getByUrl(userId, url);
        if (planToWatchMovieDTO.isPresent()) {
            planToWatchRepository.deletePlanToWatch(userId, url);
        }
    }
}
