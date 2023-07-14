package com.sparta.note.repository;

import com.sparta.note.entity.Comment;
import com.sparta.note.entity.CommentLike;
import com.sparta.note.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}