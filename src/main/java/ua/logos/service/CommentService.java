package ua.logos.service;

import ua.logos.domain.CommentDTO;

import java.util.List;

public interface CommentService {

    void saveComment(CommentDTO commentDTO);

    List<CommentDTO> findCommentByFilmId(Long id);
}
