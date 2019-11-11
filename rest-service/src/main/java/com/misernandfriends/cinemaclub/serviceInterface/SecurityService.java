package com.misernandfriends.cinemaclub.serviceInterface;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}