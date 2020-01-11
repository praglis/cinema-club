package com.misernandfriends.cinemaclub.serviceInterface;

public interface ReviewServiceLocal {
    String getNYTCriticsPicksReview();
    String getNYTMovieReview(String title);
    String getGuardianMovieReview(String title);
}
