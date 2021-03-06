/*
package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.MoviesList;
import com.misernandfriends.cinemaclub.repository.movie.MovieRepository;
import com.misernandfriends.cinemaclub.repository.user.RecommendationRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieDetailService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MoviesFetchServiceImpl;
import com.misernandfriends.cinemaclub.serviceInterface.rec.RecommendationService;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
//@ContextConfiguration(classes = SpringRestServiceApplication.class)
//@ComponentScan(basePackages = {"com.misernandfriends.cinemaclub"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RecommendationServiceImplTest {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieServiceLocal movieService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManagerFactory entityFactory;

    @Autowired
    private MovieDetailService movieFetchService;

    @Autowired
    private MoviesFetchServiceImpl moviesFetchService;

    private EntityManager entityManager;

    @Before
    public void registerInstances() {
        entityManager = entityFactory.createEntityManager();
        UserDTO user = new UserDTO();
        user.setEmail("recom@re.pl");
        user.setUsername("Recommendation");
        user.setEnrolmentDate(new Date());
        user.setStatus("A");
        userRepository.create(user);
    }

    @After
    public void cleanup() {
        UserDTO user = userRepository.findByUsername("Recommendation").get();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM RecommendationDTO WHERE userId = :userId")
                .setParameter("userId", user.getId()).executeUpdate();
        entityManager.createQuery("DELETE FROM UserDTO WHERE id = :id")
                .setParameter("id", user.getId()).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @SneakyThrows
    @Test
    public void processMovieCheckGenres() {
        UserDTO user = userRepository.findByUsername("Recommendation").get();
        MovieDTO movie = movieService.getMovieData("Troy");
        recommendationService.processMovie(user, movie);

        JSONObject movieObject = getMovieAsJSONObject("Troy");
        JSONArray genres = movieObject.getJSONArray("genre_ids");

        List<Integer> actual = recommendationRepository.getByUser(user.getId(), RecommendationDTO.Type.Category)
                .stream().map(recommendationDTO -> Integer.parseInt(recommendationDTO.getValue()))
                .collect(Collectors.toList());
        for (int i = 0; i < genres.length(); i++) {
            Integer genreId = genres.getInt(i);
            if (!actual.contains(genreId)) {
                Assert.fail("Genre id not exist in database");
            } else {
                actual.remove(genreId);
            }
        }
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void processMovieNewFavoriteMovies() throws JSONException {
        UserDTO user = userRepository.findByUsername("Recommendation").get();
        MovieDTO movie1 = movieService.getMovieData("World War Z");
        MovieDTO movie2 = movieService.getMovieData("2012");
        recommendationService.processMovie(user, movie1);
        recommendationService.processMovie(user, movie2);

        MoviesList movies;
        movies = moviesFetchService.getRecommendedMovies(user, 1, RecommendationDTO.Type.Category);
        Assert.assertTrue(movies.getTotalResults() > 0 && movies.getMovies().size() == 20);
        movies = moviesFetchService.getRecommendedMovies(user, 1, RecommendationDTO.Type.Actor);
        Assert.assertTrue(movies.getTotalResults() > 0 && movies.getMovies().size() == 20);
        movies = moviesFetchService.getRecommendedMovies(user, 1, RecommendationDTO.Type.Director);
        Assert.assertTrue(movies.getTotalResults() > 0 && movies.getMovies().size() == 20);
    }

    @SneakyThrows
    private JSONObject getMovieAsJSONObject(String movieTitle) {
        return new JSONObject(movieFetchService.getMovieByQuery("Troy")).getJSONArray("results")
                .getJSONObject(0);
    }


    @Test
    public void getMovieBaseOnTaste() {
        long start = System.currentTimeMillis();
        UserDTO user = userRepository.findByUsername("1035499").get();
        List<MovieDTO> movieBaseOnTaste = recommendationService.getMovieBaseOnTaste(user);
        Assert.assertFalse(movieBaseOnTaste.isEmpty());
        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

}*/
