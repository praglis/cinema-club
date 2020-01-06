package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MovieFetchServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieServiceLocal {

    @Autowired
    private MovieFetchServiceLocal movieDetailService;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieDTO getMovie(String movieName) {
        Optional<MovieDTO> movieOptional = movieRepository.getByTitle(movieName);
        if(movieOptional.isPresent()) {
            return movieOptional.get();
        }
        String movieByQuery = movieDetailService.getMovieByQuery(movieName);
        JSONObject json;
        try {
            json = new JSONObject(movieByQuery);
            if(!json.has("total_results") || !json.has("results")) {
                return null;
            }
            if(json.getInt("total_results") == 0) {
                return null;
            }
            JSONObject movieObj = json
                    .getJSONArray("results")
                    .getJSONObject(0);
            MovieDTO movie = new MovieDTO();
            movie.setTitle(movieName);
            movie.setApiUrl(movieObj.getString("id"));
            movieRepository.create(movie);
            return movie;
        } catch (JSONException e) {
            return null;
        }
    }
}
