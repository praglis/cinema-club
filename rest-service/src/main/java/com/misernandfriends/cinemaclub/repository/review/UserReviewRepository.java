package com.misernandfriends.cinemaclub.repository.review;

import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface UserReviewRepository extends AbstractRepository<UserReviewDTO> {

    public List<UserReviewDTO> getUserReviews(Long userId);

    public List<UserReviewDTO> getUserReviews(Long userId, int maxResults);

    List<UserReviewDTO> getUserMovieReviews(String movieUrl);

    void delete(UserReviewDTO userReviewDTO, UserDTO user);
}
