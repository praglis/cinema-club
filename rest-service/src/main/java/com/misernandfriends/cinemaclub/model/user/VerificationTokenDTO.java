package com.misernandfriends.cinemaclub.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "verification_token")
public class VerificationTokenDTO implements Serializable {

    public interface Type {
        public static String EMAIL_VERIFICATION = "E";
        public static String PASSWORD_VERIFICATION = "P";
    }

    @Column(nullable = false)
    private Date infoCD;

    private Date infoRD;

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    private UserDTO user;

    private String token;

    private Date tokenExpirationDate;

    @Column(length = 1, nullable = false)
    private String tokenType;

    @PrePersist
    public void prePersist() {
        fillExpirationDate();
    }

    @PreUpdate
    public void preUpdate() {
        fillExpirationDate();
    }

    private void fillExpirationDate() {
        if (tokenExpirationDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(infoCD);
            calendar.add(Calendar.HOUR_OF_DAY, 24);
            setTokenExpirationDate(calendar.getTime());
        }
    }

}
