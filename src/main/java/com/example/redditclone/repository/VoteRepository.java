package com.example.redditclone.repository;

import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import com.example.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    @Query(value = "SELECT * from redditclone.vote where post_id = :post_id and user_id = :user_id limit 1", nativeQuery = true)
    Optional<Vote> findTopByPostAndUserOrOrderByVoteId(@Param("post_id") Long postId, @Param("user_id") Long userId);
}
