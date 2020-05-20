package com.misernandfriends.cinemaclub.controller;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.BadgeDTO;
import com.misernandfriends.cinemaclub.model.user.RecommendationDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.pojo.config.BugReport;
import com.misernandfriends.cinemaclub.pojo.movie.Recommendation;
import com.misernandfriends.cinemaclub.pojo.movie.review.CommentReport;
import com.misernandfriends.cinemaclub.pojo.user.Badge;
import com.misernandfriends.cinemaclub.pojo.user.User;
import com.misernandfriends.cinemaclub.pojo.user.UserReport;
import com.misernandfriends.cinemaclub.serviceInterface.config.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.config.SecurityService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MoviesFetchService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.ReviewService;
import com.misernandfriends.cinemaclub.serviceInterface.rec.RecommendationService;
import com.misernandfriends.cinemaclub.serviceInterface.user.UserService;
import com.misernandfriends.cinemaclub.serviceInterface.user.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final VerificationTokenService verificationTokenService;
    private final MailService mailService;
    private final MoviesFetchService moviesFetchService;
    private final RecommendationService recommendationService;
    private final ReviewService reviewService;

    public UserController(UserService userService, SecurityService securityService, VerificationTokenService verificationTokenService, MailService mailService, MoviesFetchService moviesFetchService, RecommendationService recommendationService, ReviewService reviewService) {
        this.userService = userService;
        this.securityService = securityService;
        this.verificationTokenService = verificationTokenService;
        this.mailService = mailService;
        this.moviesFetchService = moviesFetchService;
        this.recommendationService = recommendationService;
        this.reviewService = reviewService;
    }

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
    public ResponseEntity<Object> editProfile(@RequestBody UserDTO user) {
        String currentPrincipalName = securityService.findLoggedInUsername();
        Optional<UserDTO> userFromDB = userService.findByUsername(currentPrincipalName);

        if (userFromDB.isPresent()) {
            userService.updateProfile(user, userFromDB.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO user) {
        Optional<UserDTO> checkUser = userService.findByUsername(user.getUsername());
        if (checkUser.isPresent()) {
            if (checkUser.get().getStatus().equals(UserDTO.Status.CLOSED)) {
                log.error(user.getUsername() + " has been closed");
                return ErrorResponse.createError(user.getUsername() + " has been closed");
            }
            if (checkUser.get().getStatus().equals(UserDTO.Status.BLOCKED)) {
                log.error(user.getUsername() + " has been banned");
                return ErrorResponse.createError(user.getUsername() + " has been banned");
            }
            if (!checkUser.get().getEmailConfirmed()) {
                log.error("Please active your account by clicking activation link that was sent to your email address");
                return ErrorResponse.createError("Please active your account by clicking activation link that was sent to your email address");
            }
        } else {
            log.error("Username/password is incorrect");
            return ErrorResponse.createError("Username/password is incorrect");
        }

        String transientPassword = user.getPassword();
        securityService.autoLogin(user.getUsername(), transientPassword);
        Map<String, String> body = new HashMap<>();
        body.put("username", user.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDTO user) {
        Optional<UserDTO> userExists = userService.findByUsername(user.getUsername());
        Optional<UserDTO> emailExists = userService.findByEmail(user.getEmail());
        if (userExists.isPresent()) {
            log.error("Username already taken");
            return ErrorResponse.createError("Username already taken");
        }

        if (emailExists.isPresent()) {
            if (emailExists.get().getEmailConfirmed()) {
                log.error("Email already used");
                return ErrorResponse.createError("Email already used");
            } else {
                log.error("Activation link has been already sent to email: " + emailExists.get().getEmail());
                return ErrorResponse.createError("Activation link has been already sent to email: " + emailExists.get().getEmail());
            }
        }

        userService.register(user);
        Map<String, String> body = new HashMap<>();
        body.put("username", user.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/verifyuser")
    public ResponseEntity<Object> verifyUser(@RequestParam(name = "token") String token, @RequestParam(name = "username") String username) {
        Optional<UserDTO> user = userService.findByUsername(username);
        if (!user.isPresent() || user.get().getEmailConfirmed()) {
            log.error("User cannot be activate!");
            return ErrorResponse.createError("User cannot be activate!");
        }

        verificationTokenService.verifyRegistrationToken(user.get(), token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody UserDTO userPassword, @RequestParam(name = "token") String token, @RequestParam(name = "username") String username) {
        Optional<UserDTO> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            log.error("User does not exist");
            return ErrorResponse.createError("User does not exist");
        }

        verificationTokenService.verifyChangePasswordToken(user.get(), token);
        userService.changePassword(user.get(), userPassword.getPassword());

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody UserDTO user) {
        Optional<UserDTO> userExists = userService.findByUsername(user.getUsername());
        if (!userExists.isPresent()) {
            log.error("User does not exist");
            return ErrorResponse.createError("User does not exist");
        }

        userService.resetPassword(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resetPasswordWithLoggedUser")
    public ResponseEntity<Object> resetPasswordWithLoggedUser() {
        Optional<UserDTO> userDTO = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userDTO.isPresent()) {
            log.error("User does not exist");
            return ErrorResponse.createError("User does not exist");
        }

        userService.resetPassword(userDTO.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
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
    public ResponseEntity<Object> getPreferences(@RequestParam String type, @RequestParam(required = false) Integer page) {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            log.error("User does not exist");
            return ErrorResponse.createError("User does not exist");
        }

        Recommendation body = new Recommendation();
        body.setMovies(moviesFetchService.getRecommendedMovies(userOptional.get(), page, type));
        body.setRecomVariable(recommendationService.getValues(userOptional.get(), type));
        body.setRecommendationsPresent(body.getRecomVariable().size() != 0);
        if (RecommendationDTO.Type.Similar.equals(type)) {
            body.setRecommendationsPresent(true);
        }

        return ResponseEntity.ok(body);
    }

    @PostMapping("/user/preferences/refresh")
    public ResponseEntity<Object> refreshPreferences() {
        Optional<UserDTO> userOptional = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userOptional.isPresent()) {
            log.error("User does not exist");
            return ErrorResponse.createError("User does not exist");
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

        UserReviewDTO userReviewDTO = reviewService.getUserReviewById(Long.valueOf(commentReport.getCommentId()));
        String currentLoggedUsername = securityService.findLoggedInUsername();

        userReport.setReportedComment(userReviewDTO.getStatement());
        userReport.setReportedUsername(userReviewDTO.getInfoCU().getUsername());
        userReport.setReportingUsername(currentLoggedUsername);

        mailService.sendUserReport(userReport);
    }

    @GetMapping("/badge")
    public Badge getUserBadge() {
        Optional<UserDTO> userDTO = userService.findByUsername(securityService.findLoggedInUsername());
        if (!userDTO.isPresent()) {
            throw new EntityNotFoundException();
        } else {
            return userService.getBadge(userDTO.get()).toBadgePojo();
        }
    }

    @GetMapping("/isAdmin")
    public boolean isAdminUser(){
        Optional<UserDTO> user = userService.findByUsername(securityService.findLoggedInUsername());
        if (!user.isPresent()) {
            throw new EntityNotFoundException();
        } else {
            return userService.isAdminUser(user.get());
        }
    }
}
