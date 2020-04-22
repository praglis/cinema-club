package com.misernandfriends.cinemaclub.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.PlanToWatchMovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.Favourite;
import com.misernandfriends.cinemaclub.pojo.Movie;
import com.misernandfriends.cinemaclub.pojo.QuestionnairePostBody;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PersonalMoviesListController {

    @Autowired
    private PersonalListService personalListService;

    @Autowired
    private MovieFetchServiceLocal movieService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @GetMapping("user/favourites")
    public ResponseEntity<String> getFavouriteMovies(@RequestParam(value = "user") Long id) {
        List<FavouriteDTO> favourites = personalListService.getUserFavourites(id);
        List<Movie> favouritesResponse = new ArrayList<>();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for(FavouriteDTO favourite : favourites) {
            String movieJSON = movieService.getMovieByLongId(Long.valueOf(favourite.getMovie().getApiUrl()));
            Movie movie = gson.fromJson(movieJSON, Movie.class);
            favouritesResponse.add(movie);
        }

        return new ResponseEntity<>(gson.toJson(favouritesResponse), HttpStatus.OK);
    }

    @GetMapping("user/plantowatch")
    public ResponseEntity<String> getPlanToWatchMovies(@RequestParam(value = "user") Long id) {
        List<PlanToWatchMovieDTO> planToWatch = personalListService.getUserPlanToWatch(id);
        List<Movie> planToWatchResponse = new ArrayList<>();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for(PlanToWatchMovieDTO ptw : planToWatch) {
            String movieJSON = movieService.getMovieByLongId(Long.valueOf(ptw.getMovie().getApiUrl()));
            Movie movie = gson.fromJson(movieJSON, Movie.class);
            planToWatchResponse.add(movie);
        }

        return new ResponseEntity<>(gson.toJson(planToWatchResponse), HttpStatus.OK);
    }

    @GetMapping("user/favourites/short")
    public ResponseEntity<List<Favourite>> getFavouriteMoviesShort(@RequestParam(value = "user") Long id) {
        List<FavouriteDTO> favourites = personalListService.getUserFavourites(id);
        List<Favourite> response = new ArrayList<>();

        for (FavouriteDTO favourite : favourites) {
            Favourite responseItem = new Favourite();
            responseItem.setUserId(id);
            responseItem.setMovieUrl(favourite.getMovie().getApiUrl());
            responseItem.setMovieTitle(favourite.getMovie().getTitle());
            response.add(responseItem);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("user/plantowatch/short")
    public ResponseEntity<List<Favourite>> getPlanToWatchMoviesShort(@RequestParam(value = "user") Long id) {
        List<PlanToWatchMovieDTO> planToWatch = personalListService.getUserPlanToWatch(id);
        List<Favourite> response = new ArrayList<>();

        for (PlanToWatchMovieDTO ptw : planToWatch) {
            Favourite responseItem = new Favourite();
            responseItem.setUserId(id);
            responseItem.setMovieUrl(ptw.getMovie().getApiUrl());
            responseItem.setMovieTitle(ptw.getMovie().getTitle());
            response.add(responseItem);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("user/favourites")
    public ResponseEntity<String> addFavouriteMovie(@RequestBody Favourite favourite) {
        List<FavouriteDTO> favourites = personalListService.getUserFavourites(favourite.getUserId());

        Optional<FavouriteDTO> existing = favourites
                .stream()
                .filter(e -> e.getMovie().getApiUrl().equals(favourite.getMovieUrl()))
                .findFirst();

        if (!existing.isPresent()) {
            personalListService.createFavourite(favourite.getUserId(), favourite.getMovieTitle(), favourite.getMovieUrl());
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PostMapping("user/plantowatch")
    public ResponseEntity<String> addPlanToWatchMovie(@RequestBody Favourite favourite) {
        List<PlanToWatchMovieDTO> favourites = personalListService.getUserPlanToWatch(favourite.getUserId());

        Optional<PlanToWatchMovieDTO> existing = favourites
                .stream()
                .filter(e -> e.getMovie().getApiUrl().equals(favourite.getMovieUrl()))
                .findFirst();

        if (!existing.isPresent()) {
            personalListService.createPlanToWatch(favourite.getUserId(), favourite.getMovieTitle(), favourite.getMovieUrl());
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PostMapping("user/questionnaire")
    public ResponseEntity<String> addMoviesFromQuestionnaire(@RequestBody QuestionnairePostBody body) {

        Optional<UserDTO> existingUser = userRepository.getById(body.getUserId());

        if (!existingUser.isPresent()) {
            return new ResponseEntity<>("User does not exists", HttpStatus.BAD_REQUEST);
        }

        if (!existingUser.get().getUsername().equals(securityService.findLoggedInUsername())) {
            return new ResponseEntity<>("User is not logged in\"", HttpStatus.BAD_REQUEST);
        }

        for (Movie movie: body.getMovies()) {
            MovieDTO movieDTO= new MovieDTO();
            movieDTO.setApiUrl(movie.getId().toString());
            movieDTO.setTitle(movie.getTitle());

            recommendationService.processMovie(existingUser.get(), movieDTO);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("user/favourites")
    public ResponseEntity<String> removeFavouriteMovie(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId) {
        List<FavouriteDTO> favourites = personalListService.getUserFavourites(userId);
        Optional<FavouriteDTO> existing = favourites
                .stream()
                .filter(e -> e.getMovie().getApiUrl().equals(movieId.toString()))
                .findFirst();

        if (existing.isPresent()) {
            personalListService.deleteFavourite(userId, movieId.toString());
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("user/plantowatch")
    public ResponseEntity<String> removePlanToWatchMovie(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId) {
        List<PlanToWatchMovieDTO> favourites = personalListService.getUserPlanToWatch(userId);
        Optional<PlanToWatchMovieDTO> existing = favourites
                .stream()
                .filter(e -> e.getMovie().getApiUrl().equals(movieId.toString()))
                .findFirst();

        if (existing.isPresent()) {
            personalListService.deletePlanToWatch(userId, movieId.toString());
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
