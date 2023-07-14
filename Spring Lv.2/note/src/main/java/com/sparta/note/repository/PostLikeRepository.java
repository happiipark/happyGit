package com.sparta.note.repository;

import com.sparta.note.entity.Post;
import com.sparta.note.entity.PostLike;
import com.sparta.note.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
}