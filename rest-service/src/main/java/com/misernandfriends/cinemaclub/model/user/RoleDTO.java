package com.misernandfriends.cinemaclub.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "SYS_ROLES")
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 8012462160665822728L;

    @Id
    @Column(name = "ROL_ID")
    @SequenceGenerator(name = "seq_usr_role_id", sequenceName = "seq_usr_user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_user_id")
    private Long id;

    @Column(name = "ROL_NAME")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserDTO> users;
}
