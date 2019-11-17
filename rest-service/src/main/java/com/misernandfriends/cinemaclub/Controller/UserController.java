package com.misernandfriends.cinemaclub.Controller;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.service.SecurityServiceImpl;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityServiceImpl securityService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public UsernamePasswordAuthenticationToken login(@RequestBody UserDTO user) {
        securityService.autoLogin(user.getUsername(), user.getPassword());

        return securityService.getUsernamePasswordAuthenticationToken();
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public UsernamePasswordAuthenticationToken register(@RequestBody UserDTO user) {
        UserDTO userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getUsername() + " already exists");
        }
        userService.save(user);
        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());

        return securityService.getUsernamePasswordAuthenticationToken();
    }
}