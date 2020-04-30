package com.misernandfriends.cinemaclub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.BadgeDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.*;
import com.misernandfriends.cinemaclub.serviceInterface.*;
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

    @Autowired
    private MoviesFetchServiceLocal moviesFetchService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private ReviewServiceLocal reviewServiceLocal;



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
    public ResponseEntity editProfile(@RequestBody UserDTO user) {
        String currentPrincipalName = securityService.findLoggedInUsername();
        Optional<UserDTO> userFromDB = userService.findByUsername(currentPrincipalName);
        return userService.updateProfile(user, userFromDB);
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
        return userService.resetPassword(user);
    }

    @GetMapping("/resetPasswordWithLoggedUser")
    public ResponseEntity resetPasswordWithLoggedUser() {
        Optional<UserDTO> userDTO = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userDTO.isPresent()) {
            return ErrorResponse.createError("User don't exists");
        }
        return userService.resetPassword(userDTO.get());
    }

    @GetMapping("/getUsers")
    public List<String> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/check")
    @ResponseBody
    public Boolean checkLoggedUser() {
        String name = securityService.findLoggedInUsername();
        return name != null;
    }

    @GetMapping("/user/preferences")
    public ResponseEntity getPreferences(@RequestParam String type, @RequestParam(required = false) Integer page) throws JsonProcessingException {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            return ErrorResponse.createError("User doesn't not exists");
        }
        Recommendation body = new Recommendation();
        body.setMovies(moviesFetchService.getRecommendedMovies(userOptional.get(), page, type));
        body.setRecomVariable(recommendationService.getValues(userOptional.get(), type));
        body.setRecommendationsPresent(body.getRecomVariable().size() != 0);
        if(RecommendationDTO.Type.Similar.equals(type)) {
            body.setRecommendationsPresent(true);
        }
        return ResponseEntity.ok(body);
    }

    @PostMapping("/user/preferences/refresh")
    public ResponseEntity refreshPreferences() {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            return ErrorResponse.createError("User doesn't not exists");
        }
        recommendationService.refreshSimilarMovies(userOptional.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/report/bug")
    public void sendBugReport(@RequestBody BugReport bugReport) {
        mailService.sendBugReport(bugReport);
    }

    @PostMapping("/report/user")
    public void reportUser(@RequestBody CommentReport commentReport) {
        UserReport userReport = new UserReport(commentReport);

        UserReviewDTO userReviewDTO = reviewServiceLocal.getUserReviewById(Long.valueOf(commentReport.getCommentId()));
        String currentLoggedUsername = securityService.findLoggedInUsername();

        userReport.setReportedComment(userReviewDTO.getStatement());
        userReport.setReportedUsername(userReviewDTO.getInfoCU().getUsername());
        userReport.setReportingUsername(currentLoggedUsername);

        mailService.sendUserReport(userReport);
    }

    @GetMapping("/badge")
    public BadgeDTO getUserBadge(){
        Optional<UserDTO> userDTO = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userDTO.isPresent()) {
            return null;
        }
        else{
            return userService.getBadge(userDTO.get());
        }
    }
}
