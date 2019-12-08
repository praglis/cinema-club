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
@Table(name = "reset_password_token")
public class ResetPasswordTokenDTO implements Serializable {

    @Column(nullable = false)
    private Date infoCD;

    private Date infoRD;

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    private UserDTO user;

    private String token;

    private Date tokenExpirationDate;

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
