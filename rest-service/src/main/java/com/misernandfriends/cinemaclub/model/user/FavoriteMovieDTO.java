package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USR_FAV_MOVIES")
public class FavoriteMovieDTO implements Serializable {

    private static final long serialVersionUID = 1303183361306983582L;

    @ManyToOne
    @JoinColumn(name = "FVM_USR_ID", nullable = false)
    @Id
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "FVM_MOV_ID", nullable = false)
    @Id
    private MovieDTO movie;

    @ManyToOne
    @JoinColumn(name = "FVM_CAT_ID", nullable = false)
    @Id
    private CategoryDTO category;
}
