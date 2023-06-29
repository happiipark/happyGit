package com.sparta.note.dto;

import lombok.Getter;

@Getter
public class NoteRequestDto {
    private String title; // 제목
    private String name; // 작성 자명
    private String note; // 작성 내용
    private String password; // 패스워드

}
