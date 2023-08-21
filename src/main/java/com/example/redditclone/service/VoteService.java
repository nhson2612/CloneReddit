package com.example.redditclone.service;

import com.example.redditclone.dto.VoteDto;
import com.example.redditclone.excecption.RedditException;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Vote;
import com.example.redditclone.model.VoteType;
import com.example.redditclone.repository.PostsRepository;
import com.example.redditclone.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private AuthService authService;

    public void vote(VoteDto voteDto){
        Post post = postsRepository.findByPostId(voteDto.getPostId()).orElseThrow(()->
        {
            throw new NoSuchElementException("Not found post with id = " + voteDto.getPostId());
        });
        Optional<Vote> voteOptional = voteRepository.findTopByPostAndUserOrOrderByVoteId(post.getPostId(),authService.getCurrentUser().getUserId());
        if(voteOptional.isPresent()){
            throw new RedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if(voteDto.getVoteType().equals(VoteType.UPVOTE)){
            post.setVoteCount(post.getVoteCount() + 1);
        }
        if(voteDto.getVoteType().equals(VoteType.DOWNVOTE)){
            post.setVoteCount(post.getVoteCount() -1);
        }

        voteRepository.save(mapDtoToVote(voteDto,post));
        postsRepository.save(post);
    }

    private Vote mapDtoToVote(VoteDto voteDto,Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .user(authService.getCurrentUser())
                .post(post)
                .build();
    }

}
