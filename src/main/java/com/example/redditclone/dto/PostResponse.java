package com.example.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String postName;
    private String subredditName;
    private String username;
    private String url;
    private String description;
    private String duration;
    private Integer voteCount;
    private boolean voteUp;
    private boolean voteDown;
    private Integer commentCount;
}
