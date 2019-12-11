package com.misernandfriends.cinemaclub.serviceInterface;

import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;

public interface VerificationTokenService  {

    VerificationTokenDTO generateRegistrationToken(UserDTO user);

    void verifyRegistrationToken(UserDTO user, String token);

    VerificationTokenDTO generatePasswordResetToken(UserDTO user);

    void verifyChangePasswordToken(UserDTO user, String token);

}
