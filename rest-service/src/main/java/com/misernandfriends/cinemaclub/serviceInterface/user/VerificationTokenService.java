package com.misernandfriends.cinemaclub.serviceInterface.user;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;

public interface VerificationTokenService  {
    VerificationTokenDTO generatePasswordResetToken(UserDTO user);
    VerificationTokenDTO generateRegistrationToken(UserDTO user);
    void verifyChangePasswordToken(UserDTO user, String token);
    void verifyRegistrationToken(UserDTO user, String token);
}
