package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.repository.user.VerificationTokenRepository;
import com.misernandfriends.cinemaclub.serviceInterface.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public ResponseEntity banUser(String userName) {
        Optional<UserDTO> optionalUserDTO = userRepository.findByUsername(userName);
        if(optionalUserDTO.isPresent()){
            UserDTO userDTO = optionalUserDTO.get();
            userDTO.setStatus("B");
            userRepository.update(userDTO);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @Override
    public ResponseEntity blockUser(String userName) {
        Optional<UserDTO> optionalUserDTO = userRepository.findByUsername(userName);
        if(optionalUserDTO.isPresent()){
            UserDTO userDTO = optionalUserDTO.get();
            userDTO.setStatus("L");
            userRepository.update(userDTO);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @Override
    public ResponseEntity activeUser(String userName) {
        Optional<UserDTO> optionalUserDTO = userRepository.findByUsername(userName);
        if(optionalUserDTO.isPresent()){
            UserDTO userDTO = optionalUserDTO.get();
            userDTO.setStatus("A");
            userRepository.update(userDTO);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @Override
    public ResponseEntity deleteUser(String userName) {
        Optional<UserDTO> optionalUserDTO = userRepository.findByUsername(userName);
        if(optionalUserDTO.isPresent()){
            UserDTO userDTO = optionalUserDTO.get();
            Optional<VerificationTokenDTO> optionalVerificationTokenDTO = verificationTokenRepository.getByUserIdForDelete(userDTO.getId(), VerificationTokenDTO.Type.EMAIL_VERIFICATION);
            if(optionalVerificationTokenDTO.isPresent()){
                verificationTokenRepository.delete(optionalVerificationTokenDTO.get());
            }
            Optional<VerificationTokenDTO> optionalVerificationTokenDTO2 = verificationTokenRepository.getByUserIdForDelete(userDTO.getId(), VerificationTokenDTO.Type.PASSWORD_VERIFICATION);
            if(optionalVerificationTokenDTO2.isPresent()){
                verificationTokenRepository.delete(optionalVerificationTokenDTO2.get());
            }
            userRepository.delete(userDTO);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }
}
