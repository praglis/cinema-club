package com.misernandfriends.cinemaclub.model.event;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "EVN_NOTIFICATIONS")
public class NotificationDTO implements Serializable {

    private static final long serialVersionUID = -2285030564208708033L;

    public interface Status {
        String SEND = "S";    // Notification has been send
        String TO_SEND = "T"; // Notification has not been send yet
    }

    public interface Type {
        String EVENT = "E";
        String PREMIERE = "P";
    }

    @Id
    @Column(name = "NOT_ID")
    @SequenceGenerator(name = "seq_evn_not_id", sequenceName = "seq_evn_not_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_evn_not_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOT_USR_ID")
    private UserDTO user;

    @Column(name = "NOT_STATUS", length = 1, nullable = false)
    private String status;

    @Column(name = "NOT_TYPE",length = 1, nullable = false)
    private String type;

    @Column(name = "NOT_DATE")
    private Date date;

    @Column(name = "NOT_DESCRIPTION")
    private String description;
}
