package com.sparta.note.service;

import com.sparta.note.dto.NoteRequestDto;
import com.sparta.note.dto.NoteResponseDto;
import com.sparta.note.entity.Note;
import com.sparta.note.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteResponseDto createMemo(NoteRequestDto requestDto) {
        // RequestDto -> Entity
        Note note = new Note(requestDto);

        // DB 저장
        Note saveNote = noteRepository.save(note);

        // Entity -> ResponseDto
        NoteResponseDto noteResponseDto = new NoteResponseDto(saveNote);

        return noteResponseDto;
    }
    // 게시글 조회
    public List<NoteResponseDto> getNotes() {
        return noteRepository.findAllByOrderByCreatedAtDesc().stream().map(NoteResponseDto::new).toList();
    }

    public NoteResponseDto getNote(Long noteNum) {
        Note note = findNote(noteNum);
        return new NoteResponseDto(note);
    }

    @Transactional
    public NoteResponseDto updatNote(Long noteNum, NoteRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인
        Note note = findNote(noteNum);

        // 비밀번호 체크
        note.checkPassword(requestDto.getPassword());
        // note 내용 수정
        note.setTitle(requestDto.getTitle());  // 게시물 제목 수정
        note.setName(requestDto.getName());   // 게시글 작성자 수정
        note.setNote(requestDto.getNote());  // 게시글 내용 수정

        return new NoteResponseDto(note);
    }



    public void deleteNote(Long noteNum,String password) {
        // 해당 게시글이 DB에 존재하는지 확인
        Note note = findNote(noteNum);

        // 비밀번호 체크
        note.checkPassword(password);

        //삭제
        noteRepository.delete(note);
    }

    private Note findNote(Long noteNum) {
        return noteRepository.findById(noteNum).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }


}