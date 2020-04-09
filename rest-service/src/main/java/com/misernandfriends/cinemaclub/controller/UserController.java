package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.User;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import com.misernandfriends.cinemaclub.serviceInterface.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private MailService mailService;


    //Przykład do pobierania aktualnego usera
    @GetMapping("/user")
    public ResponseEntity<User> user() {
        String currentPrincipalName = securityService.findLoggedInUsername();
        Optional<UserDTO> user = userService.findByUsername(currentPrincipalName);

        User response = new User();
        if (user.isPresent()) {
            response = response.toUserResponse(user.get());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/update")
    public void editProfile(@RequestBody UserDTO user) {
        String currentPrincipalName = securityService.findLoggedInUsername();
        Optional<UserDTO> userFromDB = userService.findByUsername(currentPrincipalName);
        userService.updateProfile(user, userFromDB);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO user) {
        Optional<UserDTO> checkUser = userService.findByUsername(user.getUsername());
        if (checkUser.isPresent()) {
            if (checkUser.get().getStatus().equals("C")) {
                return ErrorResponse.createError(user.getUsername() + " has been closed");
            }
            if (checkUser.get().getStatus().equals("B")) {
                return ErrorResponse.createError(user.getUsername() + " has been banned");
            }
            if (!checkUser.get().getEmailConfirmed()) {
                return ErrorResponse.createError("Please active your account by clicking activation link that was sent to your email address");
            }
        } else {
            return ErrorResponse.createError("Username/password is incorrect");
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
            return ErrorResponse.createError("Username already taken");
        }
        if (emailExists.isPresent()) {
            if (emailExists.get().getEmailConfirmed()) {
                return ErrorResponse.createError("Email already used");
            } else {
                return ErrorResponse.createError("Activation link has been already sent to email: " + emailExists.get().getEmail());
            }
        }
        userService.register(user);

        Map<String, String> body = new HashMap<>();
        body.put("username", user.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/verifyuser")
    public ResponseEntity verifyUser(@RequestParam(name = "token") String token,
                                     @RequestParam(name = "username") String username) {
        Optional<UserDTO> user = userService.findByUsername(username);
        if (!user.isPresent() || user.get().getEmailConfirmed()) {
            return ErrorResponse.createError("User cannot be activate!");
        }
        verificationTokenService.verifyRegistrationToken(user.get(), token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody UserDTO userPassword, @RequestParam(name = "token") String token,
                                         @RequestParam(name = "username") String username) {
        Optional<UserDTO> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            return ErrorResponse.createError("User don't exists");
        }
        verificationTokenService.verifyChangePasswordToken(user.get(), token);
        userService.changePassword(user.get(), userPassword.getPassword());

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody UserDTO user) {
        Optional<UserDTO> userExists = userService.findByUsername(user.getUsername());
        if (!userExists.isPresent()) {
            return ErrorResponse.createError("User don't exists");
        }
        mailService.sendChangePasswordEmail(userExists.get());

        Map<String, String> body = new HashMap<>();
        body.put("username", user.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/getUsers")
    public List<String> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/check")
    @ResponseBody
    public Boolean checkLoggedUser() {
        String name = securityService.findLoggedInUsername();
        if (name == null) {
            return false;
        } else {
            return true;
        }
    }
}
