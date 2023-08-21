package com.example.redditclone.controller;

import com.example.redditclone.dto.VoteDto;
import com.example.redditclone.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;


    @PostMapping()
    public ResponseEntity vote(@RequestBody VoteDto voteDto){
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
