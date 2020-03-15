package com.misernandfriends.cinemaclub.model;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USR_REPORTS")
public class ReportDTO implements Serializable {

    private static final long serialVersionUID = 5428445031484557055L;

    public interface STATUS {
        public static final String NEW = "N";
        public static final String ASSIGNED = "A";
        public static final String IN_PROGRESS = "P";
        public static final String CLOSED = "C";
        public static final String INVALID = "I";
    }

    @Id
    @Column(name = "REP_ID")
    @SequenceGenerator(name = "seq_usr_rep_id", sequenceName = "seq_usr_rep_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_rep_id")
    private Long id;

    @Column(name = "REP_INFO_CD", nullable = false)
    private Date infoCD;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REP_INFO_CU", nullable = false)
    private UserDTO infoCU;

    @Column(name = "REP_INFO_RD", nullable = false)
    private Date infoRD;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REP_INFO_RU", nullable = false)
    private UserDTO infoRU;

    @Column(name = "REP_STATUS", nullable = false, length = 1)
    private String status;

    @Column(name = "REP_DESCRIPTION")
    private String description;

    @Column(name = "REP_URL", nullable = false, length = 100)
    private String url;

    @Column(name = "REP_TYPE", nullable = false, length = 1)
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REP_ASSIGNED_USER_ID", nullable = false)
    private UserDTO assignedUser;

    @Column(name = "REP_ASSIGNED_MSG")
    private String assignedMessage;
}
