package com.misernandfriends.cinemaclub.serviceInterface;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SecurityService {
    String findLoggedInUsername();

    UsernamePasswordAuthenticationToken autoLogin(String username, String password);
}