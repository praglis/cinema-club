package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.pojo.movie.VideoResults;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Credits;
import com.misernandfriends.cinemaclub.pojo.movie.Genres;
import com.misernandfriends.cinemaclub.pojo.movie.MovieSearchCriteria;
import com.misernandfriends.cinemaclub.pojo.movie.Movie;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Person;

public interface MovieDetailService {
    Genres getGenres();
    Credits getMovieCreditsById(Integer id);
    Movie getMovieById(Integer page);
    Movie getMovieByLongId(Long id);
    MoviesList getMovieByQuery(String query);
    MoviesList getMovies(MovieSearchCriteria criteria);
    VideoResults getKeyForTrailer(Integer movieId);
    Person getPerson(Long id);
    Credits getPersonCreditsById(Long id);
}
