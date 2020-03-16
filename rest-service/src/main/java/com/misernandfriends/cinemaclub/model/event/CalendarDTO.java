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
@Table(name = "EVN_CALENDARS")
public class CalendarDTO implements Serializable {

    private static final long serialVersionUID = -3871483902668286621L;

    @Id
    @Column(name = "CAL_ID")
    @SequenceGenerator(name = "seq_evn_cal_id", sequenceName = "seq_evn_cal_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evn_cal_id")
    private Long id;

    @Column(name = "CAL_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "CAL_INFO_RD", nullable = false)
    private Date infoRD;

    @ManyToOne
    @JoinColumn(name = "CAL_USR_ID")
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "CAL_MOV_ID")
    private MovieDTO movie;

    @ManyToOne
    @JoinColumn(name = "CAL_CIN_ID")
    private CinemaDTO cinema;

    @Column(name = "CAL_DATE", nullable = false)
    private Date date;

    @Column(name = "CAL_TIME")
    private Integer time;
}
