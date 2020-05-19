package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.movie.review.Rate;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import com.misernandfriends.cinemaclub.repository.movie.PremiereRepository;
import com.misernandfriends.cinemaclub.serviceInterface.config.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.CinemaService;
import com.misernandfriends.cinemaclub.serviceInterface.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/cinema")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CinemaController {

    private final CinemaRepository cinemaRepository;
    private final PremiereRepository premiereRepository;
    private final SecurityService securityService;
    private final UserService userService;
    private final CinemaService cinemaService;

    public CinemaController(CinemaRepository cinemaRepository, PremiereRepository premiereRepository,
                            SecurityService securityService, UserService userService, CinemaService cinemaService) {
        this.cinemaRepository = cinemaRepository;
        this.premiereRepository = premiereRepository;
        this.securityService = securityService;
        this.userService = userService;
        this.cinemaService = cinemaService;
    }

    @GetMapping("/find")
    public ResponseEntity<Object> getCinema(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(cinemaRepository.searchFor(params));
    }

    @GetMapping("/{cinemaId}")
    public ResponseEntity<Object> getCinema(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(cinemaRepository.getById(cinemaId));
    }

    @GetMapping("/{cinemaId}/premiers")
    public ResponseEntity<Object> getPremieres(@PathVariable(name = "cinemaId") String cinemaId, @RequestParam Map<String, String> params) {
        Optional<CinemaDTO> cinema = cinemaRepository.getById(Long.parseLong(cinemaId));
        if (!cinema.isPresent() || cinema.get().getInfoRD() != null) {
            log.error("Cinema not exist");
            return ErrorResponse.createError("Cinema not exist");
        }

        return ResponseEntity.ok(premiereRepository.searchFor(Long.parseLong(cinemaId), params));
    }

    @PostMapping("/{cinemaId}/rate")
    public ResponseEntity<Object> rateCinema(@PathVariable String cinemaId, @RequestBody Rate rate) {
        String loggedInUsername = securityService.findLoggedInUsername();
        Optional<UserDTO> user = userService.findByUsername(loggedInUsername);
        if (!user.isPresent()) {
            log.error("User does not exist");
            throw new ApplicationException("User does not exist");
        }

        cinemaService.rateCinema(cinemaId, rate, user.get());
        return ResponseEntity.noContent().build();
    }
}
