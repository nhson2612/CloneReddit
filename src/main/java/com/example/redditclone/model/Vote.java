package com.example.redditclone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @NotNull
    @ManyToOne()
    @JoinColumn(name = "postId" , referencedColumnName = "postId")
    private Post post;
    @ManyToOne()
    @JoinColumn(name = "userId" , referencedColumnName = "userId")
    private User user;
}
