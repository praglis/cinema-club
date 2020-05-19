package com.misernandfriends.cinemaclub.model.user;

import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
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
@Table(name = "USR_MOV_RATING")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "URT_ID", nullable = false)),
        @AttributeOverride(name = "rating", column = @Column(name = "URT_RATE"))
})
@AssociationOverrides({
        @AssociationOverride(name = "user", joinColumns = @JoinColumn(name = "URT_USR_ID", nullable = false))
})
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_usr_urt_id")
public class UserMovieRatingDTO extends UserRatingDTO<MovieDTO> {

    private static final long serialVersionUID = -8015006859842525797L;

    @ManyToOne
    @JoinColumn(name = "URT_MOV_ID", nullable = false)
    private MovieDTO movie;

    @Override
    public void setReference(MovieDTO entity) {
        setMovie(entity);
    }

    @Override
    public MovieDTO getReference() {
        return getMovie();
    }
}
