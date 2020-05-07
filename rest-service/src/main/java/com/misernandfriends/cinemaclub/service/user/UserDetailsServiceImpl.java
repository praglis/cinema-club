package com.misernandfriends.cinemaclub.service.user;


import com.misernandfriends.cinemaclub.model.user.RoleDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDTO user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Username  " + userName + " not found"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (user.getRoles() != null) {
            for (RoleDTO role : user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
