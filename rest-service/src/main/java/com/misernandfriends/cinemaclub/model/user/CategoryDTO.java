package com.misernandfriends.cinemaclub.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USR_CATEGORIES")
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = -8102136359611769444L;

    @Id
    @Column(name = "CAT_ID")
    @SequenceGenerator(name = "seq_usr_cat_id", sequenceName = "seq_usr_cat_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_cat_id")
    private Long id;

    @Column(name = "CAT_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "CAT_INFO_RD")
    private Date infoRD;

    @OneToOne
    private UserDTO user;

    @Column(name = "CAT_NAME", nullable = false)
    private String name;

    @Column(name = "CAT_DESCRIPTION")
    private String description;
}
