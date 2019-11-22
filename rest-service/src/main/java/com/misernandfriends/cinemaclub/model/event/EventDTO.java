package com.misernandfriends.cinemaclub.model.event;

import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event")
public class EventDTO implements Serializable {

    public interface Status {
        public static final String ACTIVE = "A";
        public static final String INACTIVE = "I";
        public static final String SUSPENDED = "S";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    @Column(nullable = false)
    private UserDTO infoCU;

    private Date infoRD;

    private UserDTO infoRU;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, length = 1)
    private String status;

    @ManyToOne
    private MovieDTO movie;

    @ManyToOne
    private CinemaDTO cinema;

    private UserDTO owner;

    @Column(nullable = false)
    private Boolean open = false; //public

    @ManyToMany
    private Set<UserDTO> participants;

    private int maxParticipants;
}
