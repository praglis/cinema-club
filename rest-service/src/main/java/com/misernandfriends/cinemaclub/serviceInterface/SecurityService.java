package com.misernandfriends.cinemaclub.serviceInterface;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}