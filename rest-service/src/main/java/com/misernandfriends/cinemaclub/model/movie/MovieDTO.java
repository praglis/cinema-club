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
@Table(name = "MOV_MOVIES")
public class MovieDTO implements Serializable {

    private static final long serialVersionUID = -470973098276486768L;

    @Id
    @Column(name = "MOV_ID")
    @SequenceGenerator(name = "seq_mov_id", sequenceName = "seq_mov_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mov_id")
    private Long id;

    @Column(name = "MOV_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "MOV_INFO_RD")
    private Date infoRD;

    @Column(name = "MOV_API_URL", nullable = false)
    private String apiUrl;

    @Column(name = "MOV_TITLE", nullable = false)
    private String title;
}
