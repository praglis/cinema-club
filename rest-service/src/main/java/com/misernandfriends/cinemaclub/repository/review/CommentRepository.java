package com.misernandfriends.cinemaclub.repository.review;

import com.misernandfriends.cinemaclub.model.review.CommentDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface CommentRepository extends AbstractRepository<CommentDTO> {
    List<CommentDTO> getUserComments(Long userId);
    List<CommentDTO> getUserComments(Long userId, int maxResults);
}
