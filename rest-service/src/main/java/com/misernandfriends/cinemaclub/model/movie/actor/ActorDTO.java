package com.misernandfriends.cinemaclub.model.movie.actor;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MOV_ACTORS")
public class ActorDTO implements Serializable {

    private static final long serialVersionUID = 8719282548093362444L;

    @Id
    @Column(name = "ACT_ID")
    @SequenceGenerator(name = "seq_mov_act_id", sequenceName = "seq_mov_act_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mov_act_id")
    private Long id;

    @Column(name = "ACT_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "ACT_INFO_RD", nullable = false)
    private Date infoRD;

    @Column(name = "ACT_API_URL", nullable = false)
    private String apiUrl;

    @Column(name = "ACT_NAME")
    private String name;

    public static ActorDTO newInstance(String castId, String name) {
        ActorDTO object = new ActorDTO();
        object.setApiUrl(castId);
        object.setName(name);
        return object;
    }
}
