package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@Table(name = "USR_CNM_RATING")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "UCR_ID", nullable = false)),
        @AttributeOverride(name = "rating", column = @Column(name = "UCR_RATE", nullable = false))
})
@AssociationOverrides({
        @AssociationOverride(name = "user", joinColumns = @JoinColumn(name = "UCR_USR_ID", nullable = false))
})
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_usr_cnm_id")
public class UserCinemaRatingDTO extends UserRatingDTO<CinemaDTO> {

    private static final long serialVersionUID = -8015002811842525797L;

    @ManyToOne
    @JoinColumn(name = "UCR_CNM_ID", nullable = false)
    private CinemaDTO cinema;

    @Override
    public void setReference(CinemaDTO entity) {
        setCinema(entity);
    }

    @Override
    public CinemaDTO getReference() {
        return getCinema();
    }
}
