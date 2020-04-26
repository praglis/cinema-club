package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.UserLikes;
import com.misernandfriends.cinemaclub.pojo.UserReview;

import java.util.List;

public interface ReviewServiceLocal {
    String getNYTCriticsPicksReview();

    String getNYTMovieReview(String title);

    String getGuardianMovieReview(String title);

    void addUserReview(UserReview userReview, UserDTO user);

    List<UserLikes> getUserReviews(String movieUrl, UserDTO userDTO);

    UserReviewDTO getUserReviewById(Long reviewId);

    void editUserReview(UserReview userReviewData, UserDTO user);

    void removeUserReview(Long reviewId, UserDTO user);

    void likeReview(Long reviewId, UserDTO user);
}
