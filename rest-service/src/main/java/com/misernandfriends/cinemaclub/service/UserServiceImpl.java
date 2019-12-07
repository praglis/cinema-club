package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.user.RoleDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MailService mailService;


    @Override
    @Transactional
    public void register(UserDTO user) {
        user.setEnrolmentDate(DateTimeUtil.getCurrentDate());
        user.setStatus("N");
        user.setType("U");
        user.setEmailConfirmed(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        RoleDTO role = new RoleDTO();
        role.setName("USER");
        Set<RoleDTO> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.create(user);
        mailService.sendConfirmationEmail(user);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}