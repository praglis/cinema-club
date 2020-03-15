package com.misernandfriends.cinemaclub.model.cinema;

import com.misernandfriends.cinemaclub.model.AddressDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MOV_CINEMAS")
public class CinemaDTO implements Serializable {

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
}
