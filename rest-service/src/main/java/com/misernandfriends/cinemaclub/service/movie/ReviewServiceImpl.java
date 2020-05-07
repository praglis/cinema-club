package com.misernandfriends.cinemaclub.service.movie;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.review.guardian.GuardianResponse;
import com.misernandfriends.cinemaclub.pojo.movie.review.guardian.GuardianResult;
import com.misernandfriends.cinemaclub.pojo.user.UserLikes;
import com.misernandfriends.cinemaclub.pojo.user.UserReview;
import com.misernandfriends.cinemaclub.pojo.movie.review.nyt.NYTResponse;
import com.misernandfriends.cinemaclub.pojo.movie.review.nyt.NYTReview;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import com.misernandfriends.cinemaclub.repository.review.UserReviewRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.movie.ReviewService;
import com.misernandfriends.cinemaclub.utils.UrlHelper;
import com.misernandfriends.cinemaclub.utils.UrlParamEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final UserReviewRepository userReviewRepository;
    private final CinemaRepository cinemaRepository;
    private final MovieServiceLocal movieServiceLocal;
    private final UserRepository userRepository;

    public ReviewServiceImpl(UserReviewRepository userReviewRepository, CinemaRepository cinemaRepository, MovieServiceLocal movieServiceLocal, UserRepository userRepository) {
        this.userReviewRepository = userReviewRepository;
        this.cinemaRepository = cinemaRepository;
        this.movieServiceLocal = movieServiceLocal;
        this.userRepository = userRepository;
    }

    @Override
    public NYTReview getNYTMovieReview(String movieTitle) {
        String uri = new UrlHelper(CacheValue._API_URLS.NYT_API_URL_QUERY).setQuery(movieTitle).build();
        RestTemplate restTemplate = new RestTemplate();
        NYTResponse response = restTemplate.getForObject(uri, NYTResponse.class);

        if (response == null || response.getResults() == null) {
            log.debug("Not found NYT review for movie: " + movieTitle);
            return null;
        }

        for (NYTReview review : response.getResults()) {
            if (review.getDisplayTitle().equals(movieTitle)) {
                return review;
            }
        }

        log.debug("Not found NYT review for movie: " + movieTitle);
        return null;
    }

    @Override
    public GuardianResult getGuardianMovieReview(String movieTitle) {
        String uri = new UrlHelper(CacheValue._API_URLS.GUARDIAN_API_URL_QUERY).setQuery(UrlParamEncoder.encode(movieTitle)).build();
        RestTemplate restTemplate = new RestTemplate();
        GuardianResponse guardianResponse = restTemplate.getForObject(uri, GuardianResponse.class);

        if (guardianResponse == null || guardianResponse.getResponse() == null) {
            log.debug("Not found Guardian review for movie: " + movieTitle);
            return null;
        }

        for (GuardianResult review : guardianResponse.getResponse().getResults()) {
            if (review.getWebTitle().contains(movieTitle)) {
                return review;
            }
        }

        log.debug("Not found Guardian review for movie: " + movieTitle);
        return null;
    }

    @Override
    public void editUserReview(UserReview userReview, UserDTO user) {
        if (userReview.getReviewId() == null) {
            log.error("Property 'reviewId' cannot be empty");
            throw new ApplicationException("Property 'reviewId' cannot be empty");
        }

        Optional<UserReviewDTO> potentialReview = userReviewRepository.getById(userReview.getReviewId());
        if (!potentialReview.isPresent()) {
            log.error("Cannot find review with id: " + userReview.getReviewId());
            throw new ApplicationException("Cannot find review with id: " + userReview.getReviewId());
        }

        UserReviewDTO existingReview = potentialReview.get();
        existingReview.setInfoMU(user);
        existingReview.setStatement(userReview.getReviewBody());
        userReviewRepository.update(existingReview);
    }

    @Override
    public void removeUserReview(Long reviewId, UserDTO user) {
        Optional<UserReviewDTO> potentialReview = userReviewRepository.getById(reviewId);
        if (!potentialReview.isPresent()) {
            log.error("Review with id " + reviewId + " does not exists!");
            throw new ApplicationException("Review with id " + reviewId + " does not exists!");
        }

        UserReviewDTO userReview = potentialReview.get();
        if (userReview.getInfoRD() != null) {
            log.error("Review with id " + reviewId + " not exists!");
            throw new ApplicationException("Review with id " + reviewId + " not exists!");
        }

        userReviewRepository.delete(userReview, user);
    }

    @Transactional
    @Override
    public void likeReview(Long reviewId, UserDTO user) {
        Optional<UserReviewDTO> potentialReview = userReviewRepository.getById(reviewId);
        if (!potentialReview.isPresent()) {
            log.error("Review with id " + reviewId + " does not exists!");
            throw new ApplicationException("Review with id " + reviewId + " does not exists!");
        }

        UserReviewDTO userReview = potentialReview.get();
        if (LazyCache.getValue(CacheValue._USER_REVIEW.CAN_LIKE_SELF_COMMENT).equals(LazyCache.TRUE) &&
                userReview.getInfoCU().getId().equals(user.getId())) {
            log.error("You cannot like comment that you wrote!");
            throw new ApplicationException("You cannot like comment that you wrote!");
        }

        int adder;
        List<UserReviewDTO> userReviews = user.getReviews();
        if (userReview.getLikedBy().contains(user)) {
            userReviews.remove(userReview);
            user.setBadgeValue(user.getBadgeValue() - 1);
            adder = -1;
        } else {
            userReviews.add(userReview);
            user.setBadgeValue(user.getBadgeValue() + 1);
            adder = 1;
        }

        user.setReviews(userReviews);
        userReview.setLikes((long) userReview.getLikedBy().size() + adder);

        userReviewRepository.update(userReview);
        userRepository.update(user);
    }

    @Transactional
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
            userReview.setMovie(movieServiceLocal.getMovieData(userReviewData.getMovieId()));
        } else if (userReviewData.getMovieTitle() != null) {
            userReview.setMovie(movieServiceLocal.getMovieData(userReviewData.getMovieTitle()));
        }

        userReview.setParentReviewId(userReviewData.getParentReviewId());
        userReview.setStatement(userReviewData.getReviewBody());
        userReview.setInfoCU(user);
        userReview.setHighlighted(false);
        userReview.setLikes(0L);
        user.setBadgeValue(user.getBadgeValue() + 1);

        userReviewRepository.create(userReview);
        userRepository.update(user);
    }

    @Override
    public List<UserLikes> getUserReviews(String movieUrl, UserDTO user) {
        List<UserReviewDTO> userReviews = userReviewRepository.getUserMovieReviews(movieUrl);

        Map<Long, List<UserLikes>> replies = new HashMap<>();
        for (UserReviewDTO review : userReviews) {
            Long parentReviewId = review.getParentReviewId();
            if (parentReviewId != null) {
                if (!replies.containsKey(parentReviewId)) {
                    replies.put(parentReviewId, new ArrayList<>());
                }
                replies.get(parentReviewId).add(review.toUserLikes(user));
            }
        }

        return userReviews.stream()
                .filter(review -> Objects.isNull(review.getParentReviewId()))
                .map(userReview -> userReview.toUserLikes(user, replies))
                .collect(Collectors.toList());
    }

    @Override
    public UserReviewDTO getUserReviewById(Long reviewId) {
        return userReviewRepository.getUserReviewById(reviewId).orElse(null);
    }
}
