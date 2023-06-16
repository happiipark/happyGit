package com.sparta.note.entity;

import com.sparta.note.dto.NoteRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Note {
    private Long noteNum; // 게시글 번호
    private String title; // 제목
    private String name; // 작성 자명
    private String note; // 작성 내용
    private String date; // 작성 날짜

    public Note(NoteRequestDto noteRequestDto) {
        this.title = noteRequestDto.getTitle();
        this.name = noteRequestDto.getName();
        this.note = noteRequestDto.getNote();
        this.date = noteRequestDto.getDate();
    }
}
