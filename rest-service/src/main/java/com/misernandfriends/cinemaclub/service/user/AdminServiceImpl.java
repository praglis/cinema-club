package com.misernandfriends.cinemaclub.service.user;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.repository.user.VerificationTokenRepository;
import com.misernandfriends.cinemaclub.serviceInterface.user.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    public AdminServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public ResponseEntity<Object> banUser(String userName) {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if(potentialUser.isPresent()){
            UserDTO user = potentialUser.get();
            user.setStatus(UserDTO.Status.BANNED);
            userRepository.update(user);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Object> blockUser(String userName) {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if(potentialUser.isPresent()){
            UserDTO user = potentialUser.get();
            user.setStatus(UserDTO.Status.BLOCKED);
            userRepository.update(user);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Object> activeUser(String userName) {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if(potentialUser.isPresent()){
            UserDTO user = potentialUser.get();
            user.setStatus(UserDTO.Status.ACTIVE);
            userRepository.update(user);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(String userName) {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if (potentialUser.isPresent()) {
            UserDTO user = potentialUser.get();
            Optional<VerificationTokenDTO> emailVerificationToken = verificationTokenRepository
                    .getByUserIdForDelete(user.getId(), VerificationTokenDTO.Type.EMAIL_VERIFICATION);
            emailVerificationToken.ifPresent(verificationTokenRepository::delete);

            Optional<VerificationTokenDTO> passwordVerificationToken = verificationTokenRepository
                    .getByUserIdForDelete(user.getId(), VerificationTokenDTO.Type.PASSWORD_VERIFICATION);
            passwordVerificationToken.ifPresent(verificationTokenRepository::delete);

            userRepository.delete(user);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
