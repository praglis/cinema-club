package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USR_MOV_RATING")
public class UserRatingDTO implements Serializable {

    private static final long serialVersionUID = -8015006859842525797L;

    @Id
    @Column(name = "URT_ID")
    @SequenceGenerator(name = "seq_usr_urt_id", sequenceName = "seq_usr_urt_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_urt_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "URT_USR_ID", nullable = false)
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "URT_MOV_ID", nullable = false)
    private MovieDTO movie;

    @Column(name = "URT_RATE", nullable = false)
    private Integer rating;
}
