package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "user_rating")
public class UserRatingDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserDTO user;

    @ManyToOne
    private MovieDTO movie;

    @Column(nullable = false)
    private Integer rating;
}
