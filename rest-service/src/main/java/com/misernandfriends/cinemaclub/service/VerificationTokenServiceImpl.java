package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.exception.ApplicationException;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.repository.user.VerificationTokenRepository;
import com.misernandfriends.cinemaclub.serviceInterface.MailService;
import com.misernandfriends.cinemaclub.serviceInterface.VerificationTokenService;
import com.misernandfriends.cinemaclub.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Override
    public VerificationTokenDTO generateRegistrationToken(UserDTO user) {
        Optional<VerificationTokenDTO> optional = verificationTokenRepository.getByUserId(user.getId());
        if (optional.isPresent()) {
            VerificationTokenDTO verfToken = optional.get();
            verfToken.setTokenExpirationDate(null);
            verfToken.setToken(UUID.randomUUID().toString());
            return verificationTokenRepository.update(verfToken);
        } else {
            String token = UUID.randomUUID().toString();
            VerificationTokenDTO verfToken = new VerificationTokenDTO();
            verfToken.setToken(token);
            verfToken.setUser(user);
            return verificationTokenRepository.create(verfToken);
        }
    }

    @Override
    @Transactional(dontRollbackOn = ApplicationException.class)
    public void verifyRegistrationToken(UserDTO user, String token) {
        Optional<VerificationTokenDTO> optional = verificationTokenRepository.getByUserId(user.getId());
        if (!optional.isPresent()) {
            throw new ApplicationException("There is no verification needed for user: " + user.getUsername());
        }

        VerificationTokenDTO verfToken = optional.get();
        if (!verfToken.getToken().equals(token)) {
            throw new ApplicationException("Provided token not match actual token");
        }

        if (verfToken.getTokenExpirationDate().before(DateTimeUtil.getCurrentDate())) {
            mailService.sendConfirmationEmail(user);
            throw new ApplicationException("Activation link has expire, new link has been sent to your email.");
        }

        user.setEmailConfirmed(true);
        verificationTokenRepository.setAsUsed(verfToken.getUser().getId());
        userRepository.update(user);
    }

}
