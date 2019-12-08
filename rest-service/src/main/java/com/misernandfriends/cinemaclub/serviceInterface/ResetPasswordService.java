package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.ResetPasswordTokenDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;

public interface ResetPasswordService {
    ResetPasswordTokenDTO generateRegistrationToken(UserDTO user);

    void verifyChangePasswordToken(UserDTO user, String token);
}
