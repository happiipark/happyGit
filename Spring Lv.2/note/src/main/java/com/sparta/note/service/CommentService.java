package com.sparta.note.service;

import com.sparta.note.dto.CommentRequestDto;
import com.sparta.note.dto.CommentResponseDto;
import com.sparta.note.entity.*;
import com.sparta.note.repository.CommentLikeRepository;
import com.sparta.note.repository.CommentRepository;
import com.sparta.note.repository.PostRepository;
import com.sparta.note.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        Post post = postService.findPost(requestDto.getPostId());
        Comment comment = new Comment(requestDto.getBody());
        comment.setUser(user);
        comment.setPost(post);

        var savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        commentRepository.delete(comment);
    }

    @Transactional // entity 가 수정이 일어나거나 삭제가 발생하면 그 부분이 애너테이션 걸어둔 메서드가 종료되는 시점에 반영되도록!!
    // 중간에 수정해도 중간에는 반영이 되지 않는다!
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        // 요청자가 운영자 이거나 댓글 작성자(post.user) 와 요청자(user) 가 같은지 체크
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }

        comment.setBody(requestDto.getBody());

        return new CommentResponseDto(comment);
    }

    @Transactional
    // 선택한 댓글 좋아요 기능 추가
    public CommentResponseDto commentInsertLike(Long postId, Long commentId, User user) {
        Comment comment = findComment(commentId);
        // postId 받은 것과 comment DB에 저장된 postId가 다를 경우 예외 처리
        if (postId != comment.getPost().getPostId()) {
            throw new EntityNotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
        // 작성자/관리자가 좋아요를 시도할 경우 오류 코드 반환
        User targetUser = findUser(user.getId());
        if (targetUser != null) {
            // 게시글 작성자(post.user) 와 요청자(user) 가 같은지 또는 Admin 인지 체크 (아니면 예외발생)
            if (targetUser.getRole().equals(UserRoleEnum.ADMIN) || comment.getUser().equals(targetUser)) {
                throw new RejectedExecutionException("작성자/관리자는 좋아요를 누를 수 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 사용자는 존재하지 않습니다.");
        }
        // 좋아요를 이미 누른 경우 오류 코드 반환
        if (findCommentLike(user, comment) != null) {
            throw new DataIntegrityViolationException("좋아요를 이미 누르셨습니다.");
        }
        // 오류가 나지 않을 경우 해당 댓글 좋아요 추가
        commentLikeRepository.save(new CommentLike(user, comment));
        comment.insertLikeCnt();
        CommentResponseDto commentResponseDto = new CommentResponseDto(commentRepository.save(comment));
        return commentResponseDto;
    }

    // 선택한 댓글 좋아요 취소
    public CommentResponseDto commentDeleteLike(Long postId, Long commentId, User user) {
        Comment comment = findComment(commentId);
        // postId 받은 것과 comment DB에 저장된 postId가 다를 경우 예외 처리
        if (postId != comment.getPost().getPostId()) {
            throw new EntityNotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
        // 작성자/관리자가 좋아요를 시도할 경우 오류 코드 반환
        User targetUser = findUser(user.getId());
        if (targetUser != null) {
            // 게시글 작성자(post.user) 와 요청자(user) 가 같은지 또는 Admin 인지 체크 (아니면 예외발생)
            if (targetUser.getRole().equals(UserRoleEnum.ADMIN) || comment.getUser().equals(targetUser)) {
                throw new RejectedExecutionException("작성자/관리자는 좋아요를 누를 수 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 사용자는 존재하지 않습니다.");
        }
        // 좋아요를 누른 적이 없는 경우 오류 코드 반환
        if (findCommentLike(user, comment) == null) {
            throw new NoSuchElementException("좋아요를 누르시지 않았습니다.");
        }
        commentLikeRepository.delete(findCommentLike(user, comment));
        comment.deleteLikeCnt();
        CommentResponseDto commentResponseDto = new CommentResponseDto(commentRepository.save(comment));
        return commentResponseDto;
    }

    // id에 따른 댓글 찾기
    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                // 댓글이 존재하지 않을 경우 예외 처리
                new EntityNotFoundException("선택한 댓글은 존재하지 않습니다.")
        );
    }

    // 사용자와 댓글에 따른 좋아요 찾기
    private CommentLike findCommentLike(User user, Comment comment) {
        return commentLikeRepository.findByUserAndComment(user, comment).orElse(null);
    }

    // id에 따른 게시글 찾기
    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new EntityNotFoundException("선택한 게시글은 존재하지 않습니다.")
        );
    }

    //Id로 User 찾기
    private User findUser(Long id) {
        return userRepository.findByid(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다.")
        );
    }
}