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
@Table(name = "USR_FAVOURITES")
@Filters({
        @Filter(name = "favouriteFilter", condition = "infoRD IS NULL")
})
public class FavouriteDTO implements Serializable {

    private static final long serialVersionUID = 1432888537172227400L;

    @Id
    @Column(name = "FVR_ID")
    @SequenceGenerator(name = "seq_usr_fvr_id", sequenceName = "seq_usr_fvr_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr_fvr_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FVR_USR_ID")
    private UserDTO user;

    @ManyToOne
    @JoinColumn(name = "FVR_MOV_ID")
    private MovieDTO movie;

    @Column(name = "FVR_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "FVR_INFO_RD")
    private Date infoRD;
}
