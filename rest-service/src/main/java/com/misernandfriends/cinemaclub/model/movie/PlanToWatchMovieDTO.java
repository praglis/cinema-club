package com.misernandfriends.cinemaclub.model.movie;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USR_PLAN_TO_WATCH")
@Filters({
        @Filter(name = "planToWatchFilter", condition = "infoRD IS NULL")
})
public class PlanToWatchMovieDTO implements Serializable {

    @Id
    @Column(name = "PTW_ID")
    @SequenceGenerator(name = "seq_usr_ptw_id", sequenceName = "seq_usr_ptw_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_ptw_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PTW_USR_ID")
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "PTW_MOV_ID")
    private MovieDTO movie;

    @Column(name = "PTW_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "PTW_INFO_RD")
    private Date infoRD;
}
