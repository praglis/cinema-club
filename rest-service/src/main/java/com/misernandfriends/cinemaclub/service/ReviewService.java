package com.misernandfriends.cinemaclub.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.pojo.GuardianResponse;
import com.misernandfriends.cinemaclub.pojo.GuardianResult;
import com.misernandfriends.cinemaclub.pojo.UserReview;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import com.misernandfriends.cinemaclub.repository.review.UserReviewRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import org.springframework.beans.factory.annotation.Autowired;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements ReviewServiceLocal {

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieServiceLocal movieServiceLocal;

    @Override
    public String getNYTCriticsPicksReview() {
        String uri = new UrlHelper(CacheValue._API_URLS.NYT_API_URL).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getNYTMovieReview(String title) {
        String uri = new UrlHelper(CacheValue._API_URLS.NYT_API_URL_QUERY).setQuery(title).build();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getGuardianMovieReview(String title) {
        ObjectMapper m = new ObjectMapper();

        String uri = new UrlHelper(CacheValue._API_URLS.GUARDIAN_API_URL_QUERY).setQuery(title).build();
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(uri, String.class);

        GuardianResponse allMatching;
        try {
            allMatching = m.readValue(json, new TypeReference<GuardianResponse>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        if (allMatching == null || allMatching.getResponse().getResults() == null) {
            return "";
        }

        Optional<GuardianResult> optional = allMatching.getResponse().getResults().stream().filter(e -> e.getWebTitle().contains(title + " review ")).findFirst();

        if (optional.isPresent()) {
            uri = optional.get().getApiUrl() + CacheValue._API_URLS.GUARDIAN_API_KEY + "&show-fields=byline,trailText";
            return restTemplate.getForObject(uri, String.class);
        }

        return "";
    }

    public void editUserReview(UserReview userReviewData, UserDTO user) {
        if (userReviewData.getReviewId() == null) {
            throw new ApplicationException("Property 'reviewId' cannot be empty");
        }
        Optional<UserReviewDTO> reviewOptional = userReviewRepository.getById(userReviewData.getReviewId());
        if (!reviewOptional.isPresent()) {
            throw new ApplicationException("Cannot find review with id: " + userReviewData.getReviewId());
        }
        UserReviewDTO userReview = reviewOptional.get();
        userReview.setInfoMU(user);
        userReview.setStatement(userReviewData.getReviewBody());
        userReviewRepository.update(userReview);
    }

    @Override
    public void removeUserReview(Long reviewId, UserDTO user) {
        Optional<UserReviewDTO> reviewOptional = userReviewRepository.getById(reviewId);
        if (!reviewOptional.isPresent()) {
            throw new ApplicationException("Review with id " + reviewId + " does not exists!");
        }
        UserReviewDTO userReviewDTO = reviewOptional.get();
        if (userReviewDTO.getInfoRD() != null) {
            throw new ApplicationException("Review with id " + reviewId + " not exists!");
        }
        userReviewRepository.delete(userReviewDTO, user);
    }

    @Override
    public void likeReview(Long reviewId, UserDTO user) {
        Optional<UserReviewDTO> reviewOptional = userReviewRepository.getById(reviewId);
        if (!reviewOptional.isPresent()) {
            throw new ApplicationException("Review with id " + reviewId + " does not exists!");
        }
        UserReviewDTO userReviewDTO = reviewOptional.get();
        if (LazyCache.getValue(CacheValue._USER_REVIEW.CAN_LIKE_SELF_COMMENT).equals(LazyCache.TRUE) &&
                userReviewDTO.getInfoCU().getId().equals(user.getId())) {
            throw new ApplicationException("You cannot like comment that you wrote!");
        }
        userReviewDTO.setLikes(userReviewDTO.getLikes() + 1);
        userReviewRepository.update(userReviewDTO);
    }

    @Override
    public void addUserReview(UserReview userReviewData, UserDTO user) {
        if (userReviewData.getReviewId() != null) {
            editUserReview(userReviewData, user);
            return;
        }

        UserReviewDTO userReview = new UserReviewDTO();
        if (userReviewData.getCinemaId() != null) {
            cinemaRepository.getById(userReviewData.getCinemaId()).ifPresent(userReview::setCinema);
        }
        if (userReviewData.getMovieId() != null) {
            userReview.setMovie(movieServiceLocal.getMovie(userReviewData.getMovieId()));
        } else if (userReviewData.getMovieTitle() != null) {
            userReview.setMovie(movieServiceLocal.getMovie(userReviewData.getMovieTitle()));
        }
        userReview.setStatement(userReviewData.getReviewBody());
        userReview.setInfoCU(user);
        userReview.setHighlighted(false);
        userReview.setLikes(0L);
        userReviewRepository.create(userReview);
    }

    @Override
    public List<UserReviewDTO> getUserReviews(String movieUrl) {
        return userReviewRepository.getUserMovieReviews(movieUrl);
    }
}