package com.sparta.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.note.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}