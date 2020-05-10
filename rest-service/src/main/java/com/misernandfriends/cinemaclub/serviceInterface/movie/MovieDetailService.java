package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.pojo.movie.*;
import com.misernandfriends.cinemaclub.pojo.movie.crew.Credits;

public interface MovieDetailService {
    Genres getGenres();
    Credits getMovieCreditsById(Integer id);
    Movie getMovieById(Integer page);
    Movie getMovieByLongId(Long id);
    MoviesList getMovieByQuery(String query);
    MoviesList getMovies(MovieSearchCriteria criteria);
    VideoResults getKeyForTrailer(Integer movieId);
}
