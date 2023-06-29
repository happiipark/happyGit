package com.sparta.note.dto;

import com.sparta.note.entity.Note;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoteResponseDto {
    private Boolean success; // 패스워드 일치 여부
    private Long noteNum; // 게시글 번호
    private String title; // 제목
    private String name; // 작성 자명
    private String note; // 작성 내용
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public NoteResponseDto(Note note) {
        this.noteNum = note.getNoteNum();
        this.title = note.getTitle();
        this.name = note.getUsername();
        this.note = note.getNote();
        this.createAt = note.getCreatedAt();
        this.modifiedAt = note.getModifiedAt();
    }

    public NoteResponseDto(Boolean success) {
        this.success = success;
    }
}
