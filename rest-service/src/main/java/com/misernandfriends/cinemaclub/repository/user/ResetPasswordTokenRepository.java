package com.misernandfriends.cinemaclub.repository.user;

import com.misernandfriends.cinemaclub.model.user.ResetPasswordTokenDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends AbstractRepository<ResetPasswordTokenDTO> {
    Optional<ResetPasswordTokenDTO> getByUserId(Long userId);

    void setAsUsed(Long id);
}
