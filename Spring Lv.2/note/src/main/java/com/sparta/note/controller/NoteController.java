package com.sparta.note.controller;

import com.sparta.note.dto.NoteRequestDto;
import com.sparta.note.dto.NoteResponseDto;
import com.sparta.note.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    //게시글 조회
    @GetMapping("/note")
    public List<NoteResponseDto> listNote() {
        return noteService.getNotes();
    }

    //특정 게시글 조회
    @GetMapping("/note/{noteNum}")
    public NoteResponseDto getNote(@PathVariable Long noteNum) {
        return noteService.getNote(noteNum);
    }

    //게시글 작성
    @PostMapping("/note")
    public NoteResponseDto createNote(@RequestBody NoteRequestDto requestDto) {
        return noteService.createMemo(requestDto);
    }

    // 게시글 수정
    @PutMapping("/note/{noteNum}")
    public NoteResponseDto updatNote(@PathVariable Long noteNum, @RequestBody NoteRequestDto requestDto) {
        return noteService.updatNote(noteNum, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/note/{noteNum}")
    public NoteResponseDto deleteNote(@PathVariable Long noteNum, @RequestBody NoteRequestDto requestDto) {
        noteService.deleteNote(noteNum, requestDto.getPassword());
        return new NoteResponseDto(true);
    }
}
