package com.misernandfriends.cinemaclub.model.dictionary;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "SYS_DIC_ITEMS")
public class DictionaryItemDTO implements Serializable {

    private static final long serialVersionUID = 8139180724946587553L;

    @Id
    @Column(name = "DIT_ID")
    @SequenceGenerator(name = "seq_sys_dit_id", sequenceName = "seq_sys_dit_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_dit_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DIT_DIC_ID")
    private DictionaryDTO dictionary;

    @Column(name = "DIT_INFO_CD", nullable = false)
    private Date infoRD;

    @Column(name = "DIT_NAME", nullable = false, length = 45)
    private String name;

    @Column(name = "DIT_VALUE", nullable = false, length = 45)
    private String value;
}