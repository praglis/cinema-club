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
@Table(name = "report")
public class ReportDTO implements Serializable {

    public interface STATUS {
        public static final String NEW = "N";
        public static final String ASSIGNED = "A";
        public static final String IN_PROGRESS = "P";
        public static final String CLOSED = "C";
        public static final String INVALID = "I";
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

    @Column(nullable = false, length = 1)
    private String status;

    private String description;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, length = 1)
    private String type;

    private UserDTO assignedUser;

    private String assignedMessage;

}
