package com.example.redditclone.service;

import com.example.redditclone.dto.SubredditDto;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SubredditService {

    @Autowired
    private AuthService authService;

    @Autowired
    private SubredditRepository subredditRepository;

    public SubredditDto saveSubreddit(SubredditDto subredditDto){
        Subreddit subreddit = mapSubredditDtoToSubreddit(subredditDto);
        User user = authService.getCurrentUser();
        subreddit.setUser(user);
        subreddit.setCreatedDate(Instant.now());
        Subreddit save = subredditRepository.save(subreddit);
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    public List<SubredditDto> getAlllSubreddit(){
        List<SubredditDto> subreddits  = subredditRepository.findAll().stream().map(this::mapSubredditToDto).toList();
        return subreddits;
    }

    private Subreddit mapSubredditDtoToSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = Subreddit.builder()
                .name(subredditDto.getSubredditName())
                .description(subredditDto.getDescription())
                .build();
        return subreddit;
    }

    private SubredditDto mapSubredditToDto(Subreddit subreddit){
        SubredditDto dto = new SubredditDto();
        dto.setSubredditName(subreddit.getName());
        dto.setDescription(subreddit.getDescription());
        dto.setId(subreddit.getId());
        dto.setNumberOfPost(subreddit.getPosts().size());
        return dto;
    }

}
