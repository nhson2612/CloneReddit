package com.example.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubredditDto {

    private Long id;
    private String subredditName;
    private String description;
    private Integer numberOfPost;

}
