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
@Table(name = "USR_VERIFICATION_TOKENS")
public class VerificationTokenDTO implements Serializable {

    private static final long serialVersionUID = 5979000802718522459L;

    public interface Type {
        String EMAIL_VERIFICATION = "E";
        String PASSWORD_VERIFICATION = "P";
    }

    @Id
    @Column(name = "VRT_ID")
    @SequenceGenerator(name = "seq_usr_vrt_id", sequenceName = "seq_usr_vrt_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_vrt_id")
    private Long id;

    @Column(name = "VRT_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "VRT_INFO_RD")
    private Date infoRD;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VRT_USR_ID", nullable = false)
    private UserDTO user;

    @Column(name = "VRT_TOKEN")
    private String token;

    @Column(name = "VRT_TOKEN_EXP")
    private Date tokenExpirationDate;

    @Column(name = "VRT_TOKEN_TYPE", length = 1, nullable = false)
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
