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
@Table(name = "EVN_EVENTS")
public class EventDTO implements Serializable {

    private static final long serialVersionUID = -3819612654392314495L;

    public interface Status {
        String ACTIVE = "A";
        String INACTIVE = "I";
        String SUSPENDED = "S";
    }

    @Id
    @Column(name = "EVN_ID")
    @SequenceGenerator(name = "seq_evn_id", sequenceName = "seq_evn_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evn_id")
    private Long id;

    @Column(name = "EVN_INFO_CD", nullable = false)
    private Date infoCD;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVN_INFO_CU", nullable = false)
    private UserDTO infoCU;

    @Column(name = "EVN_INFO_RD")
    private Date infoRD;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVN_INFO_RU")
    private UserDTO infoRU;

    @Column(name = "EVN_NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "EVN_DESCRIPTION", length = 500)
    private String description;

    @Column(name = "EVN_STATUS", nullable = false, length = 1)
    private String status;

    @ManyToOne
    @JoinColumn(name = "EVN_MOV_ID")
    private MovieDTO movie;

    @ManyToOne
    @JoinColumn(name = "EVN_CIN_ID")
    private CinemaDTO cinema;

    @ManyToOne
    @JoinColumn(name = "EVN_OWNER_ID")
    private UserDTO owner;

    @Column(name = "EVN_OPEN", nullable = false)
    private Boolean open = false;

    @ManyToMany
    @Transient
    private Set<UserDTO> participants;

    @Column(name = "EVN_MAX_PARTICIPANTS")
    private int maxParticipants;
}
