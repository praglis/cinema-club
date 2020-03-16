package com.misernandfriends.cinemaclub.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "SYS_ADDRESSES")
public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 3929467383293948331L;

    @Id
    @Column(name = "ADR_ID")
    @SequenceGenerator(name = "seq_sys_adr_id", sequenceName = "seq_sys_adr_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_adr_id")
    private Long id;

    @Column(name = "ADR_COUNTRY", length = 45)
    private String country;

    @Column(name = "ADR_STATE", length = 45)
    private String state;

    @Column(name = "ADR_CITY", length = 45)
    private String city;

    @Column(name = "ADR_STREET", length = 45)
    private String streetName;

    @Column(name = "ADR_HOUSE_NUMBER", length = 45)
    private String houseNumber;
}
