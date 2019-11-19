package com.misernandfriends.cinemaclub.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    private Date infoRD;

    @OneToOne
    private UserDTO user;

    @Column(nullable = false)
    private String name;

    private String description;

}
