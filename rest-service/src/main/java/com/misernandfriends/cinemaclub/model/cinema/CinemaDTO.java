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
@Table(name = "cinema")
public class CinemaDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date infoCD;

    private Date infoRD;

    @Column(nullable = false)
    private String name;

    private String phoneNo;

    /**
     * Additional phone numbers separated by ';'
     */
    private String additionalPhoneNos;

    @OneToOne
    private AddressDTO address = new AddressDTO();
}
