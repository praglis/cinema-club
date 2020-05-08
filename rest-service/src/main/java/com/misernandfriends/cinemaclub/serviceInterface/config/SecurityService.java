package com.misernandfriends.cinemaclub.serviceInterface.config;

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}