package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
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
}
