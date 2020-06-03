package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.user.UserLikes;
import com.misernandfriends.cinemaclub.pojo.user.UserReview;
import com.misernandfriends.cinemaclub.serviceInterface.config.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieDetailService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.ReviewService;
import com.misernandfriends.cinemaclub.serviceInterface.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("reviews")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ReviewController {

    private final ReviewService reviewService;
    private final SecurityService securityService;
    private final UserService userService;
    private final MovieDetailService movieDetailService;

    public ReviewController(ReviewService reviewService, SecurityService securityService, UserService userService, MovieDetailService movieDetailService) {
        this.reviewService = reviewService;
        this.securityService = securityService;
        this.userService = userService;
        this.movieDetailService = movieDetailService;
    }

    @GetMapping("userReview/{movieId}")
    public ResponseEntity<Object> getUserReviews(@PathVariable String movieId) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            log.error("User doesn't exist");
            return ErrorResponse.createError("User doesn't exist");
        }
        return ResponseEntity.ok(reviewService.getUserReviews(movieId, userOptional.get()));
    }

    @GetMapping("userReviewAll")
    public ResponseEntity<Object> getAllUserReviews() {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            log.error("User doesn't exist");
            return ErrorResponse.createError("User doesn't exist");
        }
        List<UserLikes> allUserReviews = reviewService.getAllUserReviews(userOptional.get());
        for (UserLikes allUserReview : allUserReviews) {
            allUserReview.setMovie(movieDetailService.getMovieById(Integer.valueOf(allUserReview.getMovieApi())));
        }
        return ResponseEntity.ok(allUserReviews);
    }

    @PutMapping("userReview")
    public ResponseEntity<Object> addUserReview(@RequestBody UserReview userReview) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());

        if (!userOptional.isPresent()) {
            log.error("User doesn't exist");
            return ErrorResponse.createError("User doesn't exist");
        }

        UserDTO userDTO = userOptional.get();
        if (userDTO.getStatus().equals(UserDTO.Status.BLOCKED)) {
            log.error("Account is blocked");
            return ErrorResponse.createError("Account is blocked");
        }

        reviewService.addUserReview(userReview, userOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("userReview/{reviewId}/remove")
    public ResponseEntity<Object> removeUserReview(@PathVariable Long reviewId) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            log.error("User doesn't exist");
            return ErrorResponse.createError("User doesn't exist");
        }

        reviewService.removeUserReview(reviewId, userOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("userReview/{reviewId}/like")
    public ResponseEntity<Object> likeReview(@PathVariable Long reviewId) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            log.error("User doesn't exist");
            return ErrorResponse.createError("User doesn't exist");
        }

        reviewService.likeReview(reviewId, userOptional.get());
        return ResponseEntity.noContent().build();
    }
}
