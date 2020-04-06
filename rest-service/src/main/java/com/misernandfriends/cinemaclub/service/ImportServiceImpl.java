package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.serviceInterface.ImportService;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.RatingLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private MovieServiceLocal movieService;

    @Autowired
    private RatingLoaderService ratingLoaderService;

    @Override
    public void importPreferences(File moviesFile, File prefDirectory) throws IOException {
        log.info("{} - Starting importing preferences from movie file {} and preferences folder {}", "importPreferences", moviesFile, prefDirectory);
        HashMap<Integer, MovieDTO> movies = new HashMap<>();

        BufferedReader in = new BufferedReader(new FileReader(moviesFile));
        String line;
        while ((line = in.readLine()) != null) {
            String[] moviesParts = line.split(",", 3);
            int movieId = Integer.parseInt(moviesParts[0]);
            String movieTitle = moviesParts[2];
            log.info("{} - Getting movie {} - {}", "importPreferences", movieId, movieTitle);
            MovieDTO movie = movieService.getMovie(movieTitle);

            if (movie == null) {
                log.warn("{} - Skipping line: {} due to: {}", "importPreferences", movieTitle, "Can't find movie");
                continue;
            }
            movies.put(movieId, movie);

        }
        log.info("{} - Starting processing user rating", "importPreferences");

        File[] files = prefDirectory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            ratingLoaderService.processFile(file, movies);
        }
        log.info("{} - Import success", "importPreferences");

    }

    @Override
    public void importUsers(File dictionary) throws IOException {
        File[] files = dictionary.listFiles();
        if (files == null) {
            throw new ApplicationException("Directory is empty!");
        }
        log.info("{} - Starting processing dictionary {} with {} files", "importUsers", dictionary, files.length);
        for (File file : files) {
            ratingLoaderService.importUsers(file);
        }
        log.info("{} - Import success", "importUsers");
    }
}
