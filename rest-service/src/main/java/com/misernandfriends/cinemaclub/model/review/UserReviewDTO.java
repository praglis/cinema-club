package com.misernandfriends.cinemaclub.model.review;

import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USR_REVIEWS")
public class UserReviewDTO implements Serializable {

    private static final long serialVersionUID = -7714116372386785629L;

    @Id
    @Column(name = "URV_ID")
    @SequenceGenerator(name = "seq_usr_urv_id", sequenceName = "seq_usr_urv_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_urv_id")
    private Long id;

    @Column(name = "URV_INFO_CD", nullable = false)
    private Date infoCD;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "URV_INFO_CU", nullable = false)
    private UserDTO infoCU;

    @Column(name = "URV_INFO_MD")
    private Date infoMD;

    @ManyToOne
    @JoinColumn(name = "URV_INFO_MU")
    private UserDTO infoMU;

    @Column(name = "URV_INFO_RD")
    private Date infoRD;

    @ManyToOne
    @JoinColumn(name = "URV_INFO_RU")
    private UserDTO infoRU;

    @ManyToOne
    @JoinColumn(name = "URV_MOVIE_ID")
    private MovieDTO movie;

    @ManyToOne
    @JoinColumn(name = "URV_CINEMA_ID")
    private CinemaDTO cinema;

    @Column(name = "URV_STATEMENT", nullable = false)
    private String statement;

    @Column(name = "URV_HIGHLIGHTED")
    private boolean highlighted = false;

    @Column(name = "URV_LIKES")
    private Long likes;

    @ManyToMany(mappedBy = "reviewDTOS", fetch = FetchType.LAZY)
    private List<UserDTO> userLikes;
}
