package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.serviceInterface.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    //Przykład do pobierania aktualnego usera
    @GetMapping("/user")
    public ResponseEntity userName() {
        String currentPrincipalName = securityService.findLoggedInUsername();
        Map<String, String> body = new HashMap<>();
        body.put("username", currentPrincipalName);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO user) {
        Optional<UserDTO> checkUser = userService.findByUsername(user.getUsername());
        if (checkUser.isPresent()) {
            if (checkUser.get().getStatus().equals("C")) {
                return new ResponseEntity<>(user.getUsername() + " has been closed", HttpStatus.BAD_REQUEST);
            }
            if (checkUser.get().getStatus().equals("B")) {
                return new ResponseEntity<>(user.getUsername() + " has been banned", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Username/password is incorrect", HttpStatus.BAD_REQUEST);
        }

        String transientPassword = user.getPassword();
        securityService.autoLogin(user.getUsername(), transientPassword);
        Map<String, String> body = new HashMap<>();
        body.put("username", user.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO user) {
        Optional<UserDTO> userExists = userService.findByUsername(user.getUsername());
        Optional<UserDTO> emailExists = userService.findByEmail(user.getEmail());
        if (userExists.isPresent()) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        if (emailExists.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        String transientPassword = user.getPassword();
        Date date = new Date();
        user.setEnrolmentDate(date);
        user.setStatus("N");
        user.setType("U");
        userService.save(user);
        securityService.autoLogin(user.getUsername(), transientPassword);
        Map<String, String> body = new HashMap<>();
        body.put("username", user.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}