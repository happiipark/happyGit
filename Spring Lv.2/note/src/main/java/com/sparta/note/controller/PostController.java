package com.sparta.note.controller;

import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.note.dto.ApiResponseDto;
import com.sparta.note.dto.PostListResponseDto;
import com.sparta.note.dto.PostRequestDto;
import com.sparta.note.dto.PostResponseDto;
import com.sparta.note.security.UserDetailsImpl;
import com.sparta.note.service.PostService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto requestDto) {
        PostResponseDto result = postService.createPost(requestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostListResponseDto> getPosts() {
        PostListResponseDto result = postService.getPosts();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        PostResponseDto result = postService.getPostById(postId);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        try {
            PostResponseDto result = postService.updatePost(postId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        try {
            postService.deletePost(postId, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 게시물 좋아요
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponseDto> postInsertLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 오류가 나지 않을 경우 해당 댓글 좋아요 추가
        try {
            PostResponseDto responseDto = postService.postInsertLike(postId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        }
        // 게시글이 존재하지 않을 경우 오류 메시지 반환
        catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDto(illegalArgumentException.getMessage(), HttpStatus.NOT_FOUND.value()));
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


    // 좋아요 취소
    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponseDto> postDeleteLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 오류가 나지 않을 경우 해당 댓글 좋아요 취소
        try {
            PostResponseDto responseDto = postService.postDeleteLike(postId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        }
        // 게시글이 존재하지 않을 경우 오류 메시지 반환
        catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDto(illegalArgumentException.getMessage(), HttpStatus.NOT_FOUND.value()));
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