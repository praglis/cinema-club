package com.misernandfriends.cinemaclub.service.user;

import com.misernandfriends.cinemaclub.model.review.UserReviewDTO;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.model.user.VerificationTokenDTO;
import com.misernandfriends.cinemaclub.repository.review.UserReviewRepository;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import com.misernandfriends.cinemaclub.repository.user.VerificationTokenRepository;
import com.misernandfriends.cinemaclub.serviceInterface.user.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserReviewRepository userReviewRepository;

    public AdminServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, UserReviewRepository userReviewRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.userReviewRepository = userReviewRepository;
    }

    @Override
    public void banUser(String userName) throws EntityNotFoundException {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if(potentialUser.isPresent()){
            UserDTO user = potentialUser.get();
            user.setStatus(UserDTO.Status.BANNED);
            userRepository.update(user);
        } else {
            log.error("User with name " + userName + "does not exists");
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void blockUser(String userName) throws EntityNotFoundException {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if(potentialUser.isPresent()){
            UserDTO user = potentialUser.get();
            user.setStatus(UserDTO.Status.BLOCKED);
            userRepository.update(user);
        } else {
            log.error("User with name " + userName + "does not exists");
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void activeUser(String userName) throws EntityNotFoundException {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if(potentialUser.isPresent()){
            UserDTO user = potentialUser.get();
            user.setStatus(UserDTO.Status.ACTIVE);
            userRepository.update(user);
        } else {
            log.error("User with name " + userName + "does not exists");
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void deleteUser(String userName) throws EntityNotFoundException {
        Optional<UserDTO> potentialUser = userRepository.findByUsername(userName);
        if (potentialUser.isPresent()) {
            UserDTO user = potentialUser.get();
            Optional<VerificationTokenDTO> emailVerificationToken = verificationTokenRepository
                    .getByUserIdForDelete(user.getId(), VerificationTokenDTO.Type.EMAIL_VERIFICATION);
            emailVerificationToken.ifPresent(verificationTokenRepository::delete);

            Optional<VerificationTokenDTO> passwordVerificationToken = verificationTokenRepository
                    .getByUserIdForDelete(user.getId(), VerificationTokenDTO.Type.PASSWORD_VERIFICATION);
            passwordVerificationToken.ifPresent(verificationTokenRepository::delete);

            userRepository.delete(user);
        } else {
            log.error("User with name " + userName + "does not exists");
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void highlightUserReview(Long id) throws EntityNotFoundException {
        Optional<UserReviewDTO> userReview = userReviewRepository.getUserReviewById(id);
        if(userReview.isPresent()) {
            UserReviewDTO review = userReview.get();
            review.setHighlighted(!review.isHighlighted());
            userReviewRepository.update(review);
        } else {
            log.error("Review with id " + id + "does not exists");
            throw new EntityNotFoundException();
        }
    }
}
