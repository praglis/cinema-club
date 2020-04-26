package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.controller.entity.ErrorResponse;
import com.misernandfriends.cinemaclub.model.enums.RoleEnum;
import com.misernandfriends.cinemaclub.model.user.RoleDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.RoleRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    @Transactional
    public void register(UserDTO user) {
        user.setEnrolmentDate(DateTimeUtil.getCurrentDate());
        user.setStatus("N");
        user.setType("U");
        user.setEmailConfirmed(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
    @Transactional
    public ResponseEntity updateProfile(UserDTO user, Optional userFormDB) {

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return ErrorResponse.createError("Username already taken");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
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
    public ResponseEntity resetPassword(UserDTO userDTO){

        mailService.sendChangePasswordEmail(userDTO);

        Map<String, String> body = new HashMap<>();
        body.put("username", userDTO.getUsername());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
