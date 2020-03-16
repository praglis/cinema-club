package com.misernandfriends.cinemaclub.model.review;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USR_COMMENTS")
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = -3158929149767050860L;

    @Id
    @Column(name = "UCM_ID")
    @SequenceGenerator(name = "seq_usr_ucm_id", sequenceName = "seq_usr_ucm_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_ucm_id")
    private Long id;

    @Column(name = "UCM_INFO_CD", nullable = false)
    private Date infoCD;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UCM_INFO_CU", nullable = false)
    private UserDTO infoCU;

    @Column(name = "UCM_INFO_RD")
    private Date infoRD;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UCM_INFO_RU")
    private UserDTO infoRU;

    @Column(name = "UCM_DESCRIPTION", nullable = false)
    private String description;
}
