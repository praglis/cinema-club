package com.misernandfriends.cinemaclub.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MOV_PREMIERES")
public class PremiereDTO implements Serializable {

    private static final long serialVersionUID = -7493220735699911147L;

    @Id
    @Column(name = "PRM_ID")
    @SequenceGenerator(name = "seq_mov_prm_id", sequenceName = "seq_mov_prm_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mov_prm_id")
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "PRM_CINEMA_ID")
    private CinemaDTO cinema;

    @ManyToOne
    @JoinColumn(name = "PRM_MOVIE_ID")
    private MovieDTO movie;

    @Column(name = "PRM_DATE", nullable = false)
    private Date date;
}
