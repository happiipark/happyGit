package com.sparta.note.dto;

import lombok.Getter;

@Getter
public class NoteRequestDto {
    private Long noteNum; // 게시글 번호
    private String title; // 제목
    private String name; // 작성 자명
    private String note; // 작성 내용
    private String date; // 작성 날짜
}
