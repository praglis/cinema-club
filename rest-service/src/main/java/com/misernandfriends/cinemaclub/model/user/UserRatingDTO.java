package com.misernandfriends.cinemaclub.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class UserRatingDTO<T> implements Serializable {

    private static final long serialVersionUID = -8015002859842525797L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "seq_generator",strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER", nullable = false)
    private UserDTO user;

    @Column(name = "RATE", nullable = false)
    private Integer rating;


    public abstract void setReference(T entity);

    public abstract T getReference();
}
