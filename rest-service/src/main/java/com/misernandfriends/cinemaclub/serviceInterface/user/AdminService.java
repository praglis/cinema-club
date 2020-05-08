package com.misernandfriends.cinemaclub.serviceInterface.user;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public interface AdminService {
    void activeUser(String userName) throws EntityNotFoundException;
    void banUser(String userName) throws EntityNotFoundException;
    void blockUser(String userName) throws EntityNotFoundException;
    void deleteUser(String userName) throws EntityNotFoundException;
    void highlightUserReview(Long id) throws EntityNotFoundException;
}
