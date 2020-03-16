package com.misernandfriends.cinemaclub.model.dictionary;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SYS_DICTIONARIES")
public class DictionaryDTO implements Serializable {

    private static final long serialVersionUID = 7506239043800375586L;

    @Id
    @Column(name = "DIC_ID")
    @SequenceGenerator(name = "seq_sys_dic_id", sequenceName = "seq_sys_dic_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_dic_id")
    private Long id;

    @Column(name = "DIC_DOMAIN", nullable = false, length = 45)
    private String domain;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dictionary")
    private List<DictionaryItemDTO> items;
}
