package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.user.ResetPasswordTokenDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.user.ResetPasswordTokenRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.repository.user.VerificationTokenRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.ResetPasswordService;
import com.misernandfriends.cinemaclub.serviceInterface.VerificationTokenService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
    @Autowired
    ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Override
    public ResetPasswordTokenDTO generateRegistrationToken(UserDTO user) {
        Optional<ResetPasswordTokenDTO> optional = resetPasswordTokenRepository.getByUserId(user.getId());
        if (optional.isPresent()) {
            ResetPasswordTokenDTO verfToken = optional.get();
            verfToken.setTokenExpirationDate(null);
            verfToken.setToken(UUID.randomUUID().toString());
            return resetPasswordTokenRepository.update(verfToken);
        } else {
            String token = UUID.randomUUID().toString();
            ResetPasswordTokenDTO verfToken = new ResetPasswordTokenDTO();
            verfToken.setToken(token);
            verfToken.setUser(user);
            return resetPasswordTokenRepository.create(verfToken);
        }
    }

    @Override
    @Transactional(dontRollbackOn = ApplicationException.class)
    public void verifyChangePasswordToken(UserDTO user, String token) {
        Optional<ResetPasswordTokenDTO> optional = resetPasswordTokenRepository.getByUserId(user.getId());
        if (!optional.isPresent()) {
            throw new ApplicationException("There is no verification needed for user: " + user.getUsername());
        }

        ResetPasswordTokenDTO verfToken = optional.get();
        if (!verfToken.getToken().equals(token)) {
            throw new ApplicationException("Provided token not match actual token");
        }

        if (verfToken.getTokenExpirationDate().before(DateTimeUtil.getCurrentDate())) {
            throw new ApplicationException("Activation link has expire, new link has been sent to your email.");
        }

        resetPasswordTokenRepository.setAsUsed(verfToken.getUser().getId());
    }
}
