package com.example.redditclone.controller;

import com.example.redditclone.dto.SubredditDto;
import com.example.redditclone.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {
    @Autowired

    private SubredditService subredditService;

    @PostMapping()
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){
        SubredditDto response = subredditService.saveSubreddit(subredditDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubredditDto>> showAllSubreddit(){
        List<SubredditDto> subredditDtos = subredditService.getAlllSubreddit();
        return new ResponseEntity<>(subredditDtos,HttpStatus.OK);
    }

}
