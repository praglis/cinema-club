package com.misernandfriends.cinemaclub.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USR_TIERS")
public class TierDTO implements Serializable {

    private static final long serialVersionUID = -578761076195792897L;

    @Id
    @SequenceGenerator(name = "seq_usr_tier_id", sequenceName = "seq_usr_tier_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_tier_id")
    @Column(name = "TIR_ID")
    private Long id;

    @Column(name = "TIR_MIN_POINTS", nullable = false)
    private Long minPoints;

    @Column(name = "TIR_NAME", nullable = false, length = 45)
    private String name;

    @Column(name = "TIR_DESCRIPTION", length = 45)
    private String description;
}
