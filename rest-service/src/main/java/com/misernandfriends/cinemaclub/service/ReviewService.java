package com.misernandfriends.cinemaclub.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misernandfriends.cinemaclub.comparators.UserLikesComparator;
import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.GuardianResponse;
import com.misernandfriends.cinemaclub.pojo.GuardianResult;
import com.misernandfriends.cinemaclub.pojo.UserLikes;
import com.misernandfriends.cinemaclub.pojo.UserReview;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import com.misernandfriends.cinemaclub.repository.review.UserReviewRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.SecurityService;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Collections;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class ReviewService implements ReviewServiceLocal {

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieServiceLocal movieServiceLocal;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityService securityService;


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
        title = title.replaceAll("[:'’]", "");

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

        String finalTitle = title;
        Optional<GuardianResult> optional = allMatching.getResponse().getResults().stream().filter(e -> e.getWebTitle().contains(finalTitle + " review ")).findFirst();

        if (optional.isPresent()) {
            uri = optional.get().getApiUrl() + "?" + new UrlHelper(CacheValue._API_URLS.GUARDIAN_API_KEY).build() + "&show-fields=byline,trailText";
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

        int adder;
        List<UserReviewDTO> userReviewDTOS = user.getReviewDTOS();
        if (userReviewDTO.getUserLikes().contains(user)) {
            userReviewDTOS.remove(userReviewDTO);
            user.setBadgeValue(user.getBadgeValue()-1);
            adder = -1;
        } else {
            userReviewDTOS.add(userReviewDTO);
            user.setBadgeValue(user.getBadgeValue()+1);
            adder = 1;
        }
        user.setReviewDTOS(userReviewDTOS);
        userReviewDTO.setLikes(userReviewDTO.getUserLikes().stream().count() + adder);
        userReviewRepository.update(userReviewDTO);
        userRepository.update(user);

    }

    @Override
    @Transactional
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
        userReview.setParentReviewId(userReviewData.getParentReviewId());
        userReview.setStatement(userReviewData.getReviewBody());
        userReview.setInfoCU(user);
        userReview.setHighlighted(false);
        userReview.setLikes(0L);
        user.setBadgeValue(user.getBadgeValue()+1);
        userReviewRepository.create(userReview);
        userRepository.update(user);
    }

    @Override
    public List<UserLikes> getUserReviews(String movieUrl, UserDTO userDTO) {
        List<UserReviewDTO> userDTOS = userReviewRepository.getUserMovieReviews(movieUrl);

        Map<Long, List<UserLikes>> replies = new HashMap<>();
        for (UserReviewDTO dto : userDTOS) {
            Long parentReviewId = dto.getParentReviewId();
            if (parentReviewId != null) {
                if (!replies.containsKey(parentReviewId)) {
                    replies.put(parentReviewId, new ArrayList<>());
                }
                replies.get(parentReviewId).add(toUserLikes(dto, userDTO));
            }
        }

        List<UserLikes> userLikes = userDTOS.stream()
                .filter(userReviewDTO -> Objects.isNull(userReviewDTO.getParentReviewId()))
                .map(userReviewDTO -> {
                    UserLikes userLikes1 = toUserLikes(userReviewDTO, userDTO);
                    userLikes1.setReplies(replies.get(userReviewDTO.getId()));
                    return userLikes1;
                })
                .collect(Collectors.toList());

        Collections.sort(userLikes, new UserLikesComparator());
        return userLikes;
    }

    private UserLikes toUserLikes(UserReviewDTO review, UserDTO user) {
        UserLikes userLike = new UserLikes();
        userLike.setLikes(review.getLikes());
        userLike.setId(review.getId());
        userLike.setInfoCD(review.getInfoCD());
        userLike.setInfoCU(review.getInfoCU());
        userLike.setStatement(review.getStatement());
        userLike.setLiked(user.getReviewDTOS().contains(review));
        userLike.setHighlighted(review.isHighlighted());
        return userLike;
    }


    @Override
    public UserReviewDTO getUserReviewById(Long reviewId) {
        return userReviewRepository.getUserReviewById(reviewId).get();
    }
}
