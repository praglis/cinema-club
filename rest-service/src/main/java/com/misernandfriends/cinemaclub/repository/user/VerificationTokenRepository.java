package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends AbstractRepository<VerificationTokenDTO> {
    Optional<VerificationTokenDTO> getByUserId(Long userId, String type);
    Optional<VerificationTokenDTO> getByUserIdForDelete(Long userId, String type);
    void setAsUsed(Long id,String type);
}
