package com.misernandfriends.cinemaclub.model.cinema;

import com.misernandfriends.cinemaclub.model.AddressDTO;
import com.misernandfriends.cinemaclub.model.movie.RatingEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MOV_CINEMAS")
public class CinemaDTO extends RatingEntity implements Serializable {

    private static final long serialVersionUID = 3999238317195690091L;

    @Id
    @Column(name = "CIN_ID")
    @SequenceGenerator(name = "seq_mov_cin_id", sequenceName = "seq_mov_cin_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mov_cin_id")
    private Long id;

    @Column(name = "CIN_INFO_CD", nullable = false)
    private Date infoCD;

    @Column(name = "CIN_INFO_RD")
    private Date infoRD;

    @Column(name = "CIN_NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "CIN_PHONE", length = 12)
    private String phoneNo;

    /**
     * Additional phone numbers separated by ';'
     */
    @Column(name = "CIN_ADDITIONAL_PHONE", length = 100)
    private String additionalPhoneNos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CIN_ADR_ID")
    private AddressDTO address = new AddressDTO();

    @Column(name = "CIN_AVG_RATING")
    private Double rating = 0D;

    @Column(name = "CIN_COUNT_RATING")
    private Long votesNumber = 0L;
}
