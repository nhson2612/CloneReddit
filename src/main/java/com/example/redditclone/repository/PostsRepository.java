package com.example.redditclone.repository;

import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    Optional<Post> findByPostId(Long postId);
}
