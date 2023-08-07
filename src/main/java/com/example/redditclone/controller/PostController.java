package com.example.redditclone.controller;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.model.Post;
import com.example.redditclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping()
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.createPost(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<PostResponse>> showAllPost(){
        List<PostResponse> posts = postService.getAllPost();
        return  new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("id")Long id){
        PostResponse postResponse = postService.getPostById(id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping()
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<PostResponse>> getAllPostBySubreddit(@RequestParam("sr") Long subredditId){
        List<PostResponse> responses = postService.getAllPostBySubreddit(subredditId);
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

}
