package com.example.redditclone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne()
    @JoinColumn(name = "postId" , referencedColumnName = "postId")
    private Post post;
    @ManyToOne()
    @JoinColumn(name = "userId" , referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
}
