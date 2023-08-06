package com.example.redditclone.service;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.excecption.PostNotFoundException;
import com.example.redditclone.excecption.SubredditNotFoundException;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.repository.PostsRepository;
import com.example.redditclone.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostsRepository postRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private SubredditRepository subredditRepository;

    public void createPost(PostRequest postRequest){

        Post post = mapRequestToPost(postRequest);
        post.setCreateDate(Instant.now());
        post.setUser(authService.getCurrentUser());
        post.setVoteCount(0);

        postRepository.save(post);

    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> {throw new PostNotFoundException("Not found post with id = "+ id);});
        return mapPostToResponse(post);
    }

    public List<PostResponse> getAllPost(){
        return postRepository.findAll().stream().map(this::mapPostToResponse).toList();
    }

    private Post mapRequestToPost(PostRequest request){
        Subreddit subreddit = subredditRepository.findByName(request.getSubredditName()).orElseThrow(()-> new SubredditNotFoundException("Not found Subreddit name " + request.getSubredditName()));
        return Post.builder()
                .subreddit(subreddit)
                .postName(request.getPostName())
                .url(request.getUrl())
                .description(request.getDescription())
                .build();
    }

    private PostResponse mapPostToResponse(Post post){
        PostResponse response = PostResponse.builder()
                .id(post.getPostId())
                .postName(post.getPostName())
                .subredditName(post.getSubreddit().getName())
                .username(post.getUser().getUsername())
                .url(post.getUrl())
                .description(post.getDescription())
//                .duration()
//                .voteCount()
                .build();
        return response;
    }
    public List<PostResponse> getAllPostBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(()->
        { throw new SubredditNotFoundException("Not found subreddit with id = " + subredditId);});

        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(this::mapPostToResponse).toList();

    }
}
