package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.UserReview;
import com.misernandfriends.cinemaclub.serviceInterface.ReviewServiceLocal;
import com.misernandfriends.cinemaclub.serviceInterface.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("reviews")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ReviewController {

    @Autowired
    private ReviewServiceLocal reviewService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;


    @GetMapping("NYTCriticsPicks")
    public String getNYTCriticsPicksReview() {
        return reviewService.getNYTCriticsPicksReview();
    }

    @GetMapping("userReview/{movieId}")
    public ResponseEntity getUserReviews(@PathVariable String movieId) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            return ErrorResponse.createError("User doesn't not exists");
        }
        return ResponseEntity.ok(reviewService.getUserReviews(movieId, userOptional.get()));
    }

    @PutMapping("userReview")
    public ResponseEntity addUserReview(@RequestBody UserReview userReview) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());

        if (!userOptional.isPresent()) {
            return ErrorResponse.createError("User doesn't not exists");
        }
        UserDTO userDTO = userOptional.get();
        if (userDTO.getStatus().equals("L")) {
            return ErrorResponse.createError("Your account is blocked");
        }
        reviewService.addUserReview(userReview, userOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("userReview/{reviewId}/remove")
    public ResponseEntity removeUserReview(@PathVariable Long reviewId) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            return ErrorResponse.createError("User doesn't not exists");
        }
        reviewService.removeUserReview(reviewId, userOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("userReview/{reviewId}/like")
    public ResponseEntity likeReview(@PathVariable Long reviewId) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            return ErrorResponse.createError("User doesn't not exists");
        }
        reviewService.likeReview(reviewId, userOptional.get());
        return ResponseEntity.noContent().build();
    }
}
