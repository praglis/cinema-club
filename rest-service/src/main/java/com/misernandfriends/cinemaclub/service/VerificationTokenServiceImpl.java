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

    private enum VerificationResult {
        OK, RESENT, NO_VERIFICATION, NOT_MATCH
    }

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Override
    public VerificationTokenDTO generatePasswordResetToken(UserDTO user) {
        return getVerificationTokenDTO(user, VerificationTokenDTO.Type.PASSWORD_VERIFICATION);
    }

    @Override
    public VerificationTokenDTO generateRegistrationToken(UserDTO user) {
        return getVerificationTokenDTO(user, VerificationTokenDTO.Type.EMAIL_VERIFICATION);
    }

    @Override
    @Transactional(dontRollbackOn = ApplicationException.class)
    public void verifyChangePasswordToken(UserDTO user, String token) {
        VerificationResult result = verifyToken(user, token, VerificationTokenDTO.Type.PASSWORD_VERIFICATION);
        switch (result) {
            case OK:
                break;
            case NOT_MATCH:
                throw new ApplicationException("Provided token not match actual token");
            case RESENT:
                throw new ApplicationException("Link has expire, new link has been sent to your email.");
            default:
                throw new ApplicationException("Link is not working properly");
        }
    }

    @Override
    @Transactional(dontRollbackOn = ApplicationException.class)
    public void verifyRegistrationToken(UserDTO user, String token) {
        VerificationResult result = verifyToken(user, token, VerificationTokenDTO.Type.EMAIL_VERIFICATION);
        switch (result) {
            case OK:
                user.setEmailConfirmed(true);
                userRepository.update(user);
                break;
            case RESENT:
                mailService.sendConfirmationEmail(user);
                throw new ApplicationException("Activation link has expire, new link has been sent to your email.");
            case NOT_MATCH:
                throw new ApplicationException("Provided token not match actual token");
            case NO_VERIFICATION:
                throw new ApplicationException("There is no verification needed for user: " + user.getUsername());
        }
    }

    private VerificationTokenDTO getVerificationTokenDTO(UserDTO user, String type) {
        Optional<VerificationTokenDTO> optional = verificationTokenRepository.getByUserId(user.getId(), type);
        if (optional.isPresent()) {
            VerificationTokenDTO verfToken = optional.get();
            verfToken.setTokenExpirationDate(null);
            verfToken.setToken(UUID.randomUUID().toString());
            return verificationTokenRepository.update(verfToken);
        } else {
            String token = UUID.randomUUID().toString();
            VerificationTokenDTO verfToken = new VerificationTokenDTO();
            verfToken.setTokenType(type);
            verfToken.setToken(token);
            verfToken.setUser(user);
            return verificationTokenRepository.create(verfToken);
        }
    }

    private VerificationResult verifyToken(UserDTO user, String token, String type) {
        Optional<VerificationTokenDTO> optional = verificationTokenRepository.getByUserId(user.getId(), type);
        if (!optional.isPresent()) {
            return VerificationResult.NO_VERIFICATION;
        }

        VerificationTokenDTO verfToken = optional.get();
        if (!verfToken.getToken().equals(token)) {
            return VerificationResult.NOT_MATCH;
        }

        if (verfToken.getTokenExpirationDate().before(DateTimeUtil.getCurrentDate())) {
            return VerificationResult.RESENT;
        }

        verificationTokenRepository.setAsUsed(verfToken.getUser().getId(),type);
        return VerificationResult.OK;
    }

}
