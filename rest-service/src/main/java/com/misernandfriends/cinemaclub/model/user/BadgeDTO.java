package com.misernandfriends.cinemaclub.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USR_BADGE")
public class BadgeDTO implements Serializable {

    @Id
    @Column(name = "BAD_ID")
    @SequenceGenerator(name = "seq_usr_urt_id", sequenceName = "seq_usr_urt_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_urt_id")
    private Long id;

    @NotNull
    @Column(name = "BAD_NAME")
    private String name;

    @NotNull
    @Column(name = "BAD_FROM")
    private Integer valueFrom;

    @NotNull
    @Column(name = "BAD_TO")
    private Integer valueTo;
}