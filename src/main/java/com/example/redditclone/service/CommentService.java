package com.example.redditclone.service;

import com.example.redditclone.dto.CommentDto;
import com.example.redditclone.excecption.PostNotFoundException;
import com.example.redditclone.model.Comment;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.CommentRepository;
import com.example.redditclone.repository.PostsRepository;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;
    public void createComment(CommentDto commentDto){
        Post post = postsRepository.findById(commentDto.getPostId()).orElseThrow(()->{
            throw new PostNotFoundException("Not found post with id = " + commentDto.getPostId());
        });
        Comment comment = mapDtoToComment(commentDto);
        comment.setUser(authService.getCurrentUser());
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public List<CommentDto> getAllCommentForPost(Long postId){
        Post post = postsRepository.findByPostId(postId).orElseThrow(()-> {
            throw new PostNotFoundException("Not found post with id = " + postId);
        });
        return commentRepository.findAllByPost(post).stream().map(this::mapToDto).toList();
    }

    public List<CommentDto> getAllCommentForUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()-> {
           throw new UsernameNotFoundException("Not found user with username = " + username);
        });
        return commentRepository.findAllByUser(user).stream().map(this::mapToDto).toList();
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto(comment.getId(), comment.getPost().getPostId(),comment.getText(),comment.getCreatedDate(),comment.getUser().getUsername());
        return dto;
    }

    private Comment mapDtoToComment(CommentDto dto){
        Comment comment = Comment.builder()
                .text(dto.getText())
                .createdDate(Instant.now())
                .build();
        return comment;
    }

}
