package com.misernandfriends.cinemaclub.repository.review;

import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface UserReviewRepository extends AbstractRepository<UserReviewDTO> {
    List<UserReviewDTO> getUserMovieReviews(String movieUrl);
    Optional<UserReviewDTO> getUserReviewById(Long reviewId);
    void delete(UserReviewDTO userReviewDTO, UserDTO user);
}
