package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.AddressDTO;
import com.misernandfriends.cinemaclub.model.event.EventDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class UserDTO implements Serializable {

    public interface Status {
        public final String ACTIVE = "A";
        public final String NOT_VERIFIED = "N";
        public final String BANNED = "B";
        public final String CLOSED = "C";
    }

    public interface Type {
        public final String USER = "U";
        public final String ADMINISTRATOR = "A";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

    private String surname;

    @Column(nullable = false)
    private Date enrolmentDate;

    @Column(nullable = false)
    private String status;

    private Date birthday;

    @Column(nullable = false)
    private String email;

    private Boolean emailConfirmed;

    private String newEmail;

    private String phoneNo;

    @ManyToOne(cascade = CascadeType.ALL)
    private AddressDTO address = new AddressDTO();

    private String type;

    @ManyToOne
    private TierDTO tier;

    private Long points;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<RoleDTO> roles;

    @Transient
    private String passwordConfirm;

    @ManyToMany(mappedBy = "participants")
    private Set<EventDTO> events;

    public UserDTO() {

    }

    public UserDTO(String username, String password, String passwordConfirm) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}