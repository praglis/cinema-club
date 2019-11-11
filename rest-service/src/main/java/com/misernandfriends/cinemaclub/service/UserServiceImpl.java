package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.User;
import com.misernandfriends.cinemaclub.repository.RoleRepository;
import com.misernandfriends.cinemaclub.repository.UserRepository;
import com.misernandfriends.cinemaclub.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.create(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}