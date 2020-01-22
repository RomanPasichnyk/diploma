package ua.logos.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.logos.domain.CommentDTO;
import ua.logos.entity.CommentEntity;
import ua.logos.repository.CommentRepository;
import ua.logos.repository.FilmRepository;
import ua.logos.service.CommentService;
import ua.logos.utils.ObjectMapperUtils;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapperUtils modelMapper;

    @Override
    public void saveComment(CommentDTO commentDTO) {
        CommentEntity commentEntity = modelMapper.map(commentDTO, CommentEntity.class);
        commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentDTO> findCommentByFilmId(Long id) {
        List<CommentEntity> commentEntity = commentRepository.findByFilmId(id);
        List<CommentDTO> commentDTOS = modelMapper.mapAll(commentEntity, CommentDTO.class);
        return commentDTOS;
    }
}
