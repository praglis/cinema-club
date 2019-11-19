package com.misernandfriends.cinemaclub.model.dictionary;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "dictionary_item")
public class DictionaryItemDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DictionaryDTO dictionary;

    private Date infoRD;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;
}
