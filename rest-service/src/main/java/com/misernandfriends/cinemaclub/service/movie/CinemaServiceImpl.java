package com.misernandfriends.cinemaclub.service.movie;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.user.UserCinemaRatingDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.review.Rate;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import com.misernandfriends.cinemaclub.repository.user.UserCinemaRatingRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.movie.CinemaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {

    private final UserCinemaRatingRepository userCinemaRatingRepository;
    private final UserRepository userRepository;
    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(UserCinemaRatingRepository userCinemaRatingRepository, UserRepository userRepository, CinemaRepository cinemaRepository) {

        this.userCinemaRatingRepository = userCinemaRatingRepository;
        this.userRepository = userRepository;
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public void rateCinema(String cinemaId, Rate rate, UserDTO user) {
        UserCinemaRatingDTO ratingDTO = new UserCinemaRatingDTO();
        Integer oldRate = null;
        Optional<UserCinemaRatingDTO> byUser = userCinemaRatingRepository.getByUser(user.getId(), cinemaId);
        if (byUser.isPresent()) {
            ratingDTO = byUser.get();
            oldRate = ratingDTO.getRating();
        }
        Optional<CinemaDTO> cinemaOptional = cinemaRepository.getById(Integer.parseInt(cinemaId));
        if (!cinemaOptional.isPresent()) {
            throw new ApplicationException("Cinema not found");
        }
        CinemaDTO cinema = cinemaOptional.get();
        ratingDTO.setCinema(cinemaOptional.get());
        ratingDTO.setUser(user);
        ratingDTO.setRating(rate.getRate());
        user.setBadgeValue(user.getBadgeValue() + 1);
        cinema.recalculateRating(oldRate, rate.getRate());
        userRepository.update(user);
        userCinemaRatingRepository.create(ratingDTO);
        cinemaRepository.update(cinema);
    }
}
