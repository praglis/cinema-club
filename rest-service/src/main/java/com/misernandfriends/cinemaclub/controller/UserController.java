package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.serviceInterface.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public UsernamePasswordAuthenticationToken login(@RequestBody UserDTO user) {


        return  securityService.autoLogin(user.getUsername(), user.getPassword());
    }

    @PostMapping("/register")
    public UsernamePasswordAuthenticationToken register(@RequestBody UserDTO user) {
        Optional<UserDTO> userExists = userService.findByUsername(user.getUsername());
        if (userExists.isPresent()) {
            throw new BadCredentialsException("User with username: " + user.getUsername() + " already exists");
        }

        userService.save(user);
        return  securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());
    }
}