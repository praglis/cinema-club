package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.enums.RoleEnum;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.user.BadgeDTO;
import com.misernandfriends.cinemaclub.model.user.RoleDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.UserRatingDTO;
import com.misernandfriends.cinemaclub.repository.user.BadgeRepository;
import com.misernandfriends.cinemaclub.repository.user.RoleRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRatingRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.RatingLoaderService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Override
    @Transactional
    public void register(UserDTO user) {
        user.setEnrolmentDate(DateTimeUtil.getCurrentDate());
        user.setStatus("N");
        user.setType("U");
        user.setEmailConfirmed(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBadgeValue(0);
        Optional<RoleDTO> userRole = roleRepository.findByName(RoleEnum.USER.getValue());
        if (userRole.isPresent()) {
            user.setRoles(Arrays.asList(userRole.get()));
        } else {
            RoleDTO role = new RoleDTO();
            role.setName(RoleEnum.USER.getValue());
            roleRepository.create(role);
            user.setRoles(Arrays.asList(role));
        }
        userRepository.create(user);
        mailService.sendConfirmationEmail(user);
    }

    @Override
    @Transactional
    public void changePassword(UserDTO user, String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.update(user);
    }

    @Override
    public boolean isAdminUser(UserDTO userDTO) {
        List<RoleDTO> userRoles = userDTO.getRoles();
        if(userRoles.stream().anyMatch(e -> CacheValue._USER_ROLES.ADMIN.toString().equals(e.getName()))) {
            return true;
        } else return false;
    }

    @Override
    @Transactional
    public ResponseEntity updateProfile(UserDTO user, Optional userFormDB) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ErrorResponse.createError("Username already taken");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ErrorResponse.createError("Email already taken");
        }
        if (userFormDB.isPresent()) {
            UserDTO userToUpdate = ((UserDTO) (userFormDB.get()));
            userToUpdate.setName(user.getName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setSurname(user.getSurname());
            userToUpdate.setBirthday(user.getBirthday());
            userToUpdate.setPhoneNo(user.getPhoneNo());
            userToUpdate.setAddress(user.getAddress());
            userRepository.update(userToUpdate);
        }

        return ResponseEntity.ok().build();
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<String> getAllUsers() {
        List<UserDTO> userDTOList = userRepository.findAll();
        List<String> userNames = userDTOList.stream().map(UserDTO::getUsername).collect(Collectors.toList());
        return userNames;
    }

    @Override
    public List<String> getAllAdminEmails() {
        List<UserDTO> userDTOList = userRepository.findAll();
        List<String> adminEmails = new LinkedList<>();

        for (UserDTO userDTO : userDTOList) {
            boolean isAdmin = userDTO.getRoles().stream()
                    .anyMatch(r -> r.getName().equals("ADMIN"));
            if (isAdmin) adminEmails.add(userDTO.getEmail());
        }

        return adminEmails;
    }

    @Override
    public ResponseEntity resetPassword(UserDTO userDTO) {

        mailService.sendChangePasswordEmail(userDTO);

        Map<String, String> body = new HashMap<>();
        body.put("username", userDTO.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Override
    public BadgeDTO getBadge(UserDTO userDTO) {
        return badgeRepository.findBadgeFromValue(userDTO.getBadgeValue())
                .orElse(null);
    }

    @Slf4j
    @Service
    public static class RatingLoaderServiceImpl implements RatingLoaderService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private UserRatingRepository userRatingRepository;

        @Async
        @Override
        @org.springframework.transaction.annotation.Transactional
        public void processFile(File file, Map<Integer, MovieDTO> movies) throws IOException {
            log.info("Processing file {}", file);
            long startDate = System.currentTimeMillis();
            BufferedReader prefScanner = new BufferedReader(new FileReader(file));
            String line;
            while ((line = prefScanner.readLine()) != null) {
                String[] parts = line.split(",");
                UserDTO user = getUser(parts[0]);
                if (user == null) {
                    log.warn("Cant find user with name {}", parts[0]);
                    continue;
                }
                MovieDTO movie = movies.get(Integer.parseInt(parts[1]));
                if (movie == null) {
                    log.warn("Cant find movie with id {}", parts[1]);
                    continue;
                }
                UserRatingDTO userRating = new UserRatingDTO();
                userRating.setMovie(movie);
                userRating.setRating(Integer.parseInt(parts[2]));
                userRating.setUser(user);
                userRatingRepository.create(userRating);
            }
            log.info("Processing file {} ended int {}", file, (System.currentTimeMillis() - startDate));
        }

        @Override
        @Async
        public void importUsers(File file) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            log.info("Starting processing file {}", file);
            while ((line = reader.readLine()) != null) {
                createUser(line.trim());
            }
            log.info("File {} processed", file);
        }

        private UserDTO createUser(String userIdString) {
            Optional<UserDTO> byUsername = userRepository.findByUsername(userIdString);
            if (byUsername.isPresent()) {
                return byUsername.get();
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userIdString);
            userDTO.setEnrolmentDate(DateTimeUtil.getCurrentDate());
            userDTO.setEmail(userIdString + "@preferences.test");
            userDTO.setStatus(UserDTO.Status.NOT_VERIFIED);
            userRepository.create(userDTO);
            return userDTO;
        }

        private UserDTO getUser(String userIdString) {
            Optional<UserDTO> byUsername = userRepository.findByUsername(userIdString);
            return byUsername.orElse(null);
        }
    }
}
