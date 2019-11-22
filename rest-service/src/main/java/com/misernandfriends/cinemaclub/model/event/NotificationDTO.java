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
@Table(name = "notification")
public class NotificationDTO implements Serializable {

    public interface Status {
        public static String SEND = "S";    // Notification has been send
        public static String TO_SEND = "T"; // Notification has not been send yet
    }

    public interface Type {
        public static String EVENT = "E";
        public static String PREMIERE = "P";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserDTO user;

    @Column(length = 1, nullable = false)
    private String status;

    @Column(length = 1, nullable = false)
    private String type;

    private Date date;

    private String description;
}
