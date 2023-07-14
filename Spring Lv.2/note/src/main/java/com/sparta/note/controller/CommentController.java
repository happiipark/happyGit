package com.sparta.note.controller;

import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.note.dto.ApiResponseDto;
import com.sparta.note.dto.CommentRequestDto;
import com.sparta.note.dto.CommentResponseDto;
import com.sparta.note.security.UserDetailsImpl;
import com.sparta.note.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto result = commentService.createComment(requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        try {
            CommentResponseDto result = commentService.updateComment(commentId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 선택한 댓글 좋아요 추가
    @PostMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<ApiResponseDto> commentInsertLike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 오류가 나지 않을 경우 해당 댓글 좋아요 추가
        try {
            CommentResponseDto responseDto = commentService.commentInsertLike(postId, commentId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        }
        // postId 받은 것과 comment DB에 저장된 postId가 다를 경우, 댓글이 존재하지 않을 경우 오류 메시지 반환
        catch (EntityNotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDto(notFoundException.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
        // 작성한 유저/관리자가 좋아요를 시도할 경우 오류 메시지 반환
        catch (RejectedExecutionException rejectedExecutionException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(rejectedExecutionException.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        // 사용자가 이미 좋아요를 누른 경우 오류 메시지 반환
        catch (DataIntegrityViolationException dataIntegrityViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseDto(dataIntegrityViolationException.getMessage(), HttpStatus.CONFLICT.value()));
        }
    }

    // 선택한 댓글 좋아요 취소
    @DeleteMapping("/{postId}/comments/{commentId}/like")
    public ResponseEntity<ApiResponseDto> commentDeleteLike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 오류가 나지 않을 경우 해당 댓글 좋아요 취소
        try {
            CommentResponseDto responseDto = commentService.commentDeleteLike(postId, commentId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        }
        // postId 받은 것과 comment DB에 저장된 postId가 다를 경우, 댓글이 존재하지 않을 경우 오류 메시지 반환
        catch (EntityNotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDto(notFoundException.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
        // 작성한 유저/관리자가 좋아요를 시도할 경우 오류 메시지 반환
        catch (RejectedExecutionException rejectedExecutionException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDto(rejectedExecutionException.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        // 사용자가 좋아요를 누른 적이 없는 경우 오류 메시지 반환
        catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseDto(noSuchElementException.getMessage(), HttpStatus.CONFLICT.value()));
        }
    }


}