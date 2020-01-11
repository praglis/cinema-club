package com.misernandfriends.cinemaclub.model.movie;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Filters({
        @Filter(name = "movieFiler", condition = "infoRD IS NULL")
})
@Table(name = "movie")
public class MovieDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    private Date infoRD;

    private String apiUrl;

    @Column(nullable = false)
    private String title;

}
