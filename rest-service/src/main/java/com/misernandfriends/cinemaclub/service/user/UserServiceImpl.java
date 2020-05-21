package com.misernandfriends.cinemaclub.service.user;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
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
import com.misernandfriends.cinemaclub.serviceInterface.config.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.movie.RatingLoaderService;
import com.misernandfriends.cinemaclub.serviceInterface.user.UserService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final RoleRepository roleRepository;
    private final BadgeRepository badgeRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, @Lazy MailService mailService, RoleRepository roleRepository, BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
        this.roleRepository = roleRepository;
        this.badgeRepository = badgeRepository;
    }

    @Transactional
    @Override
    public void register(UserDTO user) {
        user.setEnrolmentDate(DateTimeUtil.getCurrentDate());
        user.setStatus(UserDTO.Status.NOT_VERIFIED);
        user.setType(UserDTO.Type.USER);
        user.setEmailConfirmed(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setBadgeValue(0);

        Optional<RoleDTO> userRole = roleRepository.findByName(RoleEnum.USER.getValue());
        if (userRole.isPresent()) {
            user.setRoles(Collections.singletonList(userRole.get()));
        } else {
            RoleDTO role = new RoleDTO();
            role.setName(RoleEnum.USER.getValue());
            roleRepository.create(role);
            user.setRoles(Collections.singletonList(role));
        }

        userRepository.create(user);
        mailService.sendConfirmationEmail(user);
    }

    @Transactional
    @Override
    public void changePassword(UserDTO user, String password) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.update(user);
    }

    @Override
    public boolean isAdminUser(UserDTO user) {
        return user
                .getRoles()
                .stream()
                .anyMatch(e -> CacheValue._USER_ROLES.ADMINISTRATOR.toString().equals(e.getName()));
    }

    @Transactional
    @Override
    public void updateProfile(UserDTO user, UserDTO potentialUser) {

        if (user.getName() != null &&
                potentialUser.getName() != null &&
                !user.getName().equals(potentialUser.getName()) &&
                userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.error("Username already taken");
            throw new ApplicationException("Username already taken");
        }

        if (user.getEmail() != null &&
                potentialUser.getEmail() != null &&
                !user.getEmail().equals(potentialUser.getEmail()) &&
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.error("Email already taken");
            throw new ApplicationException("Email already taken");
        }

        potentialUser.setName(user.getName());
        potentialUser.setEmail(user.getEmail());
        potentialUser.setSurname(user.getSurname());
        potentialUser.setBirthday(user.getBirthday());
        potentialUser.setPhoneNo(user.getPhoneNo());
        potentialUser.setAddress(user.getAddress());
        potentialUser.setHasQuestionnaire(user.getHasQuestionnaire());
        potentialUser.setFirstLogIn(user.getFirstLogIn());
        userRepository.update(potentialUser);
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
        List<UserDTO> users = userRepository.findAll();
        return users.stream()
                .map(UserDTO::getUsername)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllAdminEmails() {
        List<UserDTO> userDTOList = userRepository.findAll();
        List<String> adminEmails = new LinkedList<>();

        for (UserDTO userDTO : userDTOList) {
            boolean isAdmin = userDTO.getRoles().stream()
                    .anyMatch(r -> r.getName().equals(UserDTO.Type.ADMINISTRATOR));
            if (isAdmin) adminEmails.add(userDTO.getEmail());
        }

        return adminEmails;
    }

    @Override
    public void resetPassword(UserDTO userDTO) {
        mailService.sendChangePasswordEmail(userDTO);
    }

    @Override
    public BadgeDTO getBadge(UserDTO user) {
        return badgeRepository
                .findBadgeFromValue(user.getBadgeValue())
                .orElse(null);
    }

    @Slf4j
    @Service
    public static class RatingLoaderServiceImpl implements RatingLoaderService {

        private final UserRepository userRepository;
        private final UserRatingRepository userRatingRepository;

        public RatingLoaderServiceImpl(UserRepository userRepository, UserRatingRepository userRatingRepository) {
            this.userRepository = userRepository;
            this.userRatingRepository = userRatingRepository;
        }

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

        private void createUser(String userIdString) {
            Optional<UserDTO> byUsername = userRepository.findByUsername(userIdString);
            if (byUsername.isPresent()) {
                return;
            }

            UserDTO user = new UserDTO();
            user.setUsername(userIdString);
            user.setEnrolmentDate(DateTimeUtil.getCurrentDate());
            user.setEmail(userIdString + "@preferences.test");
            user.setStatus(UserDTO.Status.NOT_VERIFIED);
            userRepository.create(user);
        }

        private UserDTO getUser(String userIdString) {
            Optional<UserDTO> byUsername = userRepository.findByUsername(userIdString);
            return byUsername.orElse(null);
        }
    }
}
