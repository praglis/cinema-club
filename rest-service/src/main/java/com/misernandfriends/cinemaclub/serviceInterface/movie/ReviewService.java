package com.misernandfriends.cinemaclub.serviceInterface.movie;

import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.user.UserLikes;
import com.misernandfriends.cinemaclub.pojo.user.UserReview;
import com.misernandfriends.cinemaclub.pojo.movie.review.guardian.GuardianResult;
import com.misernandfriends.cinemaclub.pojo.movie.review.nyt.NYTReview;

import java.util.List;

public interface ReviewService {
    NYTReview getNYTMovieReview(String title);
    UserReviewDTO getUserReviewById(Long reviewId);
    GuardianResult getGuardianMovieReview(String title);
    List<UserLikes> getUserReviews(String movieUrl, UserDTO userDTO);
    List<UserLikes> getAllUserReviews(UserDTO userDTO);
    void addUserReview(UserReview userReview, UserDTO user);
    void editUserReview(UserReview userReviewData, UserDTO user);
    void likeReview(Long reviewId, UserDTO user);
    void removeUserReview(Long reviewId, UserDTO user);
}
