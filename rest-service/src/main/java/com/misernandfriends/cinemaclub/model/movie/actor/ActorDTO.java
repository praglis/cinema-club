package com.misernandfriends.cinemaclub.model.movie.actor;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "actor")
public class ActorDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    @Column(nullable = false)
    private Date infoRD;

    @Column(nullable = false)
    private String apiUrl;

}
