package com.misernandfriends.cinemaclub.model.review;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    @Column(nullable = false)
    private UserDTO infoCU;

    private Date infoRD;

    private UserDTO infoRU;

    @Column(nullable = false)
    private String description;
}
