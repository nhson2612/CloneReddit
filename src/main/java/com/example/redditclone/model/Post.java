package com.example.redditclone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    private String url;
    private String description;
    private Integer voteCount;
    @ManyToOne()
    @JoinColumn(name = "userId" , referencedColumnName = "userId")
    private User user;
    private Instant createDate;
    @ManyToOne()
    @JoinColumn(name = "id" , referencedColumnName = "id")
    private Subreddit subreddit;
}
