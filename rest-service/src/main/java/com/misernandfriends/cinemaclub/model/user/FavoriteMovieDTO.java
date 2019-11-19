package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "favorite_movie")
public class FavoriteMovieDTO implements Serializable {

    @ManyToOne
    @Id
    private UserDTO user;

    @ManyToOne
    @Id
    private MovieDTO movie;

    @ManyToOne
    @Id
    private CategoryDTO category;
}
