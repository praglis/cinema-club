package com.misernandfriends.cinemaclub.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.misernandfriends.cinemaclub.model.movie.FavouriteDTO;
import com.misernandfriends.cinemaclub.pojo.Favourite;
import com.misernandfriends.cinemaclub.pojo.Movie;
import com.misernandfriends.cinemaclub.serviceInterface.FavouriteService;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
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
    private FavouriteService favouriteService;

    @Autowired
    private MovieFetchServiceLocal movieService;

    @GetMapping("user/favourites")
    public ResponseEntity<String> getFavouriteMovies(@RequestParam(value = "user") Long id) {
        List<FavouriteDTO> favourites = favouriteService.getUserFavourites(id);
        List<Movie> favouritesResponse = new ArrayList<>();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for(FavouriteDTO favourite : favourites) {
            String movieJSON = movieService.getMovieByLongId(Long.valueOf(favourite.getMovie().getApiUrl()));
            Movie movie = gson.fromJson(movieJSON, Movie.class);
            favouritesResponse.add(movie);
        }

        return new ResponseEntity<>(gson.toJson(favouritesResponse), HttpStatus.OK);
    }

    @GetMapping("user/favourites/short")
    public ResponseEntity<List<Favourite>> getFavouriteMoviesShort(@RequestParam(value = "user") Long id) {
        List<FavouriteDTO> favourites = favouriteService.getUserFavourites(id);
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

    @PostMapping("user/favourites")
    public ResponseEntity<String> addFavouriteMovie(@RequestBody Favourite favourite) {
        List<FavouriteDTO> favourites = favouriteService.getUserFavourites(favourite.getUserId());

        Optional<FavouriteDTO> existing = favourites
                .stream()
                .filter(e -> e.getMovie().getApiUrl().equals(favourite.getMovieUrl()))
                .findFirst();

        if (!existing.isPresent()) {
            favouriteService.createFavourite(favourite.getUserId(), favourite.getMovieTitle(), favourite.getMovieUrl());
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("user/favourites")
    public ResponseEntity<String> removeFavouriteMovie(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId) {
        List<FavouriteDTO> favourites = favouriteService.getUserFavourites(userId);
        Optional<FavouriteDTO> existing = favourites
                .stream()
                .filter(e -> e.getMovie().getApiUrl().equals(movieId.toString()))
                .findFirst();

        if (existing.isPresent()) {
            favouriteService.deleteFavourite(userId, movieId.toString());
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
