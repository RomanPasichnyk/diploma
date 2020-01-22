package ua.logos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.logos.domain.CommentDTO;
import ua.logos.service.CommentService;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO){
        commentDTO.setCommentsDate(LocalDate.now());
        commentDTO.setCommentsTime(LocalTime.now());
        commentService.saveComment(commentDTO);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("{filmId}")
    public ResponseEntity<?> getAllCommentsByFilm(@PathVariable("filmId") Long id) {
        List<CommentDTO> commentDTOS = commentService.findCommentByFilmId(id);
        return new ResponseEntity<List<CommentDTO>>(commentDTOS, HttpStatus.OK);
    }



}
