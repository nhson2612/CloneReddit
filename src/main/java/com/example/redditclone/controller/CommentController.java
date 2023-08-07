package com.example.redditclone.controller;


import com.example.redditclone.dto.CommentDto;
import com.example.redditclone.model.Comment;
import com.example.redditclone.service.CommentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping()
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity createPost(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/by-user/{username}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<CommentDto>> showAllCommentByUser(@PathVariable("username")String username){
        List<CommentDto> response =  commentService.getAllCommentForUser(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-post/{postId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<CommentDto>> getAllCommentByPost(@PathVariable("postId")Long postId){
        List<CommentDto> response = commentService.getAllCommentForPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
