package com.sparta.note.controller;

import com.sparta.note.dto.NoteRequestDto;
import com.sparta.note.dto.NoteResponseDto;
import com.sparta.note.entity.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    private final JdbcTemplate jdbcTemplate;

    public NoteController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //게시글 작성
    @PostMapping("/note")
    public NoteResponseDto createNote(@RequestBody NoteRequestDto noteRequestDto) {
        // RequestDto -> entity  => 요청 데이터에서 객체에 담는 과정
        Note note = new Note(noteRequestDto);
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체
        String sql = "INSERT INTO note ( title, name, note, date) VALUES (?,?,?,?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, note.getTitle());
                    preparedStatement.setString(2, note.getName());
                    preparedStatement.setString(3, note.getNote());
                    preparedStatement.setString(4, note.getDate());
                    return preparedStatement;
                },
                keyHolder);
        // DB 인서트 후 받아온 기본 키 확인
        Long noteNum = keyHolder.getKey().longValue();
        note.setNoteNum(noteNum);
        // Entity -> ResponseDto
        NoteResponseDto noteResponseDto = new NoteResponseDto(note);
        return noteResponseDto;
    }


    @GetMapping("/note")
    public List<NoteResponseDto> listNote() {
        //DB 조회
        String sql = "SELECT * FROM NOTE ORDER BY date DESC";

        return jdbcTemplate.query(sql, new RowMapper<NoteResponseDto>() {
            @Override
            public NoteResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long noteNum = rs.getLong("noteNum");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String note = rs.getString("note");
                String date = rs.getString("date");
                return new NoteResponseDto(noteNum, title, name, note, date);
            }
        });

    }


    @PutMapping("/note/{noteNum}")
    public Long updateMemo(@PathVariable Long noteNum, @RequestBody NoteRequestDto noteRequestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Note note = findById(noteNum);
        if (note != null) {
            // memo 내용 수정
            String sql = "UPDATE note SET title = ?, name = ?, note = ?, date = ? WHERE noteNum = ?";
            jdbcTemplate.update(sql, noteRequestDto.getTitle(), noteRequestDto.getName(), noteRequestDto.getNote(), noteRequestDto.getDate(), noteNum);

            return noteNum;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    @DeleteMapping("/note/{noteNum}")
    public Long deleteMemo(@PathVariable Long noteNum) {
        // 해당 메모가 DB에 존재하는지 확인
        Note note = findById(noteNum);
        if (note != null) {
            // memo 삭제
            String sql = "DELETE FROM note WHERE noteNum = ?";
            jdbcTemplate.update(sql, noteNum);

            return noteNum;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    private Note findById(Long noteNum) {
        // DB 조회
        String sql = "SELECT * FROM note WHERE noteNum = ? ";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Note note = new Note();
                note.setTitle(resultSet.getString("title"));
                note.setName(resultSet.getString("name"));
                note.setNote(resultSet.getString("note"));
                note.setDate(resultSet.getString("date"));
                return note;
            } else {
                return null;
            }
        }, noteNum);
    }
}
