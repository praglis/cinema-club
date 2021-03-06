package com.misernandfriends.cinemaclub.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misernandfriends.cinemaclub.model.AddressDTO;
import com.misernandfriends.cinemaclub.model.event.EventDTO;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USR_USERS")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1831528581331720348L;

    public interface Status {
        String ACTIVE = "A";
        String NOT_VERIFIED = "N";
        String BANNED = "B";
        String BLOCKED = "L";
        String CLOSED = "C";
    }

    public interface Type {
        String USER = "U";
        String ADMINISTRATOR = "A";
    }

    @Id
    @Column(name = "USR_ID")
    @SequenceGenerator(name = "seq_usr_user_id", sequenceName = "seq_usr_user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_user_id")
    private Long id;

    @Column(name = "USR_USERNAME", nullable = false, unique = true, length = 45)
    private String username;

    @Column(name = "USR_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USR_NAME", length = 45)
    private String name;

    @Column(name = "USR_SURNAME", length = 45)
    private String surname;

    @Column(name = "USR_ENROLMENT_DATE", nullable = false)
    private Date enrolmentDate;

    @Column(name = "USR_ACCOUNT_STATUS", nullable = false, length = 1)
    private String status;

    @Column(name = "USR_BIRTHDATE")
    private Date birthday;

    @Column(name = "USR_EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "USR_EMAIL_CONFIRMED")
    private Boolean emailConfirmed;

    @Column(name = "USR_EMAIL_NEW", unique = true, length = 150)
    private String newEmail;

    @Column(name = "USR_PHONE_NO", length = 45)
    private String phoneNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USR_ADR_ID")
    private AddressDTO address = new AddressDTO();

    @Column(name = "USR_TYPE", length = 1)
    private String type;

    @ManyToOne
    @JoinColumn(name = "USR_TIER_ID")
    private TierDTO tier;

    @Column(name = "USR_POINTS")
    private Long points;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USR_USR_ROLES",
            joinColumns = @JoinColumn(
                    name = "USR_ID", referencedColumnName = "USR_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROL_ID", referencedColumnName = "ROL_ID"))
    private List<RoleDTO> roles;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "USR_USR_REVIEWS",
            joinColumns = @JoinColumn(
                    name = "USR_ID", referencedColumnName = "USR_ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "URV_ID", referencedColumnName = "URV_ID"))
    private List<UserReviewDTO> reviews;

    @Transient
    private String passwordConfirm;

    @ManyToMany(mappedBy = "participants")
    @Transient
    private Set<EventDTO> events;

    @Column(name = "USR_BADGE")
    private Integer badgeValue;


    @Column(name = "USR_HAS_QUESTIONNAIRE", columnDefinition="BOOLEAN DEFAULT false")
    private Boolean hasQuestionnaire = false;


    @Column(name = "USR_FIRST_LOGIN", columnDefinition="BOOLEAN DEFAULT true")
    private Boolean firstLogIn = true;

    public UserDTO(String username, String password, String passwordConfirm) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
