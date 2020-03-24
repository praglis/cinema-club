package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.enums.RoleEnum;
import com.misernandfriends.cinemaclub.model.user.RoleDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.RoleRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        if(userRole.isPresent()) {
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
    public void changePassword(UserDTO user, String password){
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.update(user);
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
}
