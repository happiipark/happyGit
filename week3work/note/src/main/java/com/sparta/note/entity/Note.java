package com.sparta.note.entity;

import com.sparta.note.dto.NoteRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Table(name = "note") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Note extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto_increment
    private Long noteNum; // 게시글 번호
    private String title; // 제목
    @Column(nullable = false)
    private String name; // 작성 자명
    @Column(nullable = false, length = 500)
    private String note; // 작성 내용
    @Column(nullable = false)
    private String password;


    public Note(NoteRequestDto noteRequestDto) {
        this.title = noteRequestDto.getTitle();
        this.name = noteRequestDto.getName();
        this.note = noteRequestDto.getNote();
        this.password = noteRequestDto.getPassword();

    }

    // Setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void checkPassword(String inputPassword) {
        if (!password.equals(inputPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
