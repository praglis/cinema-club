package com.misernandfriends.cinemaclub.model.review;

import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_review")
public class UserReviewDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    @Column(nullable = false)
    private UserDTO infoCU;

    private Date infoRD;

    private UserDTO infoRU;

    private MovieDTO movie;

    private CinemaDTO cinema;

    @Column(nullable = false)
    private String statement;

    private boolean highlighted = false;

    private Long likes;
}
