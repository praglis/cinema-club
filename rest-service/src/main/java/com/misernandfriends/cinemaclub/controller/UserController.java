package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/login")
    public void Username(){

    }

    @GetMapping("/register")
    public void registration(){

    }

    //Przykład do pobierania aktualnego usera
    @GetMapping("/user")
    public ResponseEntity userName(){
        String currentPrincipalName = securityService.findLoggedInUsername();

        return new ResponseEntity<String>("GET Response : "
                + currentPrincipalName, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO user) {
        String transientPassword = user.getPassword();
        securityService.autoLogin(user.getUsername(), transientPassword);
        return new ResponseEntity<String>("GET Response : "
                + user.getUsername(), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO user) {
        Optional<UserDTO> userExists = userService.findByUsername(user.getUsername());
        if (userExists.isPresent()) {
            throw new BadCredentialsException("User with username: " + user.getUsername() + " already exists");
        }
        String transientPassword = user.getPassword();
        Date date = new Date();
        user.setEnrolmentDate(date);
        user.setStatus("A");
        userService.save(user);
        securityService.autoLogin(user.getUsername(), transientPassword);
        return new ResponseEntity<String>("GET Response : "
                + user.getUsername(), HttpStatus.OK);
    }




}