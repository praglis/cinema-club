package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import com.misernandfriends.cinemaclub.repository.movie.PremiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cinema")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CinemaController {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private PremiereRepository premiereRepository;


    @GetMapping("/find")
    public ResponseEntity getCinema(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(cinemaRepository.searchFor(params));
    }

    @GetMapping("/{cinemaId}/premiers")
    public ResponseEntity getPremieres(@PathVariable(name = "cinemaId") String cinemaId, @RequestParam Map<String, String> params) {
        Optional<CinemaDTO> cinema = cinemaRepository.getById(Long.parseLong(cinemaId));
        if (!cinema.isPresent() || cinema.get().getInfoRD() != null) {
            return ErrorResponse.createError("Cinema not exist");
        }
        return ResponseEntity.ok(premiereRepository.searchFor(Long.parseLong(cinemaId), params));
    }
}
