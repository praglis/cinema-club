package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USR_MOV_SIMILAR")
public class UserSimilarMovieDTO implements Serializable {

    private static final long serialVersionUID = -5463378783957893572L;

    @Id
    @Column(name = "MUS_ID")
    @SequenceGenerator(name = "seq_mus_id", sequenceName = "seq_mus_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mus_id")
    private Long id;

    @Column(name = "MUS_INFO_CD", nullable = false)
    private Date infoCD;

    @ManyToOne
    @JoinColumn(name = "MUS_MOV_ID", nullable = false)
    private MovieDTO movie;

    @ManyToOne
    @JoinColumn(name = "MUS_USR_ID", nullable = false)
    private UserDTO user;

}
