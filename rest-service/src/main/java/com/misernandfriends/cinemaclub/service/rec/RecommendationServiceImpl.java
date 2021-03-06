package com.misernandfriends.cinemaclub.service.rec;

import com.misernandfriends.cinemaclub.model.cache.GenreCache;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.actor.ActorDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.UserMovieRatingDTO;
import com.misernandfriends.cinemaclub.model.user.UserSimilarMovieDTO;
import com.misernandfriends.cinemaclub.pojo.movie.Genre;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Cast;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Credits;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Crew;
import com.misernandfriends.cinemaclub.repository.movie.actor.ActorRepository;
import com.misernandfriends.cinemaclub.repository.user.FavouriteRepository;
import com.misernandfriends.cinemaclub.repository.user.RecommendationRepository;
import com.misernandfriends.cinemaclub.repository.user.UserMovieRatingRepository;
import com.misernandfriends.cinemaclub.repository.user.UserSimilarMovieRepository;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieDetailService;
import com.misernandfriends.cinemaclub.serviceInterface.rec.RecommendationService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private static final Double START_FIT_LEVEL = 20d;
    private static final Double CHANGE_VALUE = 50d;

    private final MovieDetailService movieDetail;
    private final RecommendationRepository recommendationRepository;
    private final ActorRepository actorRepository;
    private final UserMovieRatingRepository userMovieRatingRepository;
    private final FavouriteRepository favouriteRepository;
    private final UserSimilarMovieRepository userSimilarMovieRepository;

    public RecommendationServiceImpl(MovieDetailService movieDetail, RecommendationRepository recommendationRepository,
                                     ActorRepository actorRepository, UserMovieRatingRepository userMovieRatingRepository,
                                     FavouriteRepository favouriteRepository, UserSimilarMovieRepository userSimilarMovieRepository) {

        this.movieDetail = movieDetail;
        this.recommendationRepository = recommendationRepository;
        this.actorRepository = actorRepository;
        this.userMovieRatingRepository = userMovieRatingRepository;
        this.favouriteRepository = favouriteRepository;
        this.userSimilarMovieRepository = userSimilarMovieRepository;
    }

    @Override
    public void processMovie(UserDTO user, MovieDTO movie) {
        List<Genre> genres = movieDetail.getMovieById(Integer.parseInt(movie.getApiUrl())).getGenres();
        for (Genre genre : genres) {
            String genreId = String.valueOf(genre.getId());
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

        for (Crew crewObject : crew) {
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
    public List<MovieDTO> getMovieBaseOnTaste(UserDTO user) {
        List<MovieDTO> movies = userSimilarMovieRepository.getForUser(user.getId());
        if (!movies.isEmpty()) {
            return movies;
        }

        int maxResults = 100;
        int maxMovies = 50;

        List<FavouriteDTO> favourites = favouriteRepository.getUserFavourites(
                user.getId(),
                DateTimeUtil.minusDate(DateTimeUtil.getCurrentDate(), 1, Calendar.MONTH),
                maxResults
        );

        List<String> moviesUrls = favourites
                .stream()
                .map(favouriteDTO -> favouriteDTO.getMovie().getApiUrl())
                .collect(Collectors.toList());

        if (moviesUrls.size() < maxResults) {
            List<UserMovieRatingDTO> rating = userMovieRatingRepository.getUserBestRated(
                    user.getId(),
                    maxResults - moviesUrls.size()
            );

            moviesUrls.addAll(rating
                    .stream()
                    .map(userRatingDTO -> userRatingDTO.getMovie().getApiUrl())
                    .collect(Collectors.toList())
            );
        }

        long moviesNumber;
        LongAdder adder = new LongAdder();
        if (moviesUrls.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, Integer> similarTaste = recommendationRepository.getSimilarUser(moviesUrls, user.getId());
        similarTaste.forEach((aLong, integer) -> adder.add(integer));
        moviesNumber = adder.longValue();

        movies = new ArrayList<>();
        for (Long userId : similarTaste.keySet()) {
            int moviesToAdd = (int) ((similarTaste.get(userId) / (double) moviesNumber) * maxMovies);
            movies.addAll(recommendationRepository.findBestMoviesForBy(user.getId(), userId, moviesToAdd));
        }

        Date date = DateTimeUtil.getCurrentDate();
        for (MovieDTO movie : movies) {
            UserSimilarMovieDTO similar = new UserSimilarMovieDTO();
            similar.setMovie(movie);
            similar.setUser(user);
            similar.setInfoCD(date);
            userSimilarMovieRepository.create(similar);
        }

        return movies;
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
        List<RecommendationDTO> recommendations = recommendationRepository
                .getRecommendation(userDTO.getId(), type, 5);

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

    @Override
    public void refreshSimilarMovies(UserDTO userDTO) {
        userSimilarMovieRepository.clearForUser(userDTO.getId());
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
