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
@Table(name = "premiere")
public class PremiereDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private CinemaDTO cinema;

    @ManyToOne
    private MovieDTO movie;

    @Column(nullable = false)
    private Date date;
}
