package com.misernandfriends.cinemaclub.model.event;

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
@Table(name = "calendar")
public class CalendarDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    @Column(nullable = false)
    private Date infoRD;

    @ManyToOne
    private UserDTO user;

    @ManyToOne
    private MovieDTO movie;

    @ManyToOne
    private CinemaDTO cinema;

    @Column(nullable = false)
    private Date date;

    private Integer time;

}
