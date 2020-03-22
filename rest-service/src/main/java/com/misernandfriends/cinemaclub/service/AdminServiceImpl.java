package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            userDTO.setBanned(true);
            userRepository.update(userDTO);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with name " + userName + "does not exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @Override
    public List<String> getAllUsers() {
        List<UserDTO> userDTOList = userRepository.findAll();
        List<String> userNames = userDTOList.stream().map(UserDTO::getUsername).collect(Collectors.toList());
        return userNames;
    }
}
