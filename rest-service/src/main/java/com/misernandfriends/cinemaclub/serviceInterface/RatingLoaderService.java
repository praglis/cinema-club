package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface RatingLoaderService {

    void processFile(File file, Map<Integer, MovieDTO> movies) throws IOException;

    void importUsers(File file) throws IOException;
}
