package com.sparta.note.dto;

import com.sparta.note.entity.Note;
import lombok.Getter;

@Getter
public class NoteResponseDto {
    private Long noteNum; // 게시글 번호
    private String title; // 제목
    private String name; // 작성 자명
    private String note; // 작성 내용
    private String date; // 작성 날짜

    public NoteResponseDto(Note note) {
        this.noteNum = note.getNoteNum();
        this.title = note.getTitle();
        this.name = note.getName();
        this.note = note.getNote();
        this.date = note.getDate();
    }

    public NoteResponseDto(Long noteNum, String title, String name, String note, String date) {
        this.noteNum = noteNum;
        this.title = title;
        this.name = name;
        this.note = note;
        this.date = date;
    }
}
