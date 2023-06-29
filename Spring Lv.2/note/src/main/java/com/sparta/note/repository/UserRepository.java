package com.sparta.note.repository;

import com.sparta.note.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Oprional : Null 값을 체크하기 위해 만들어짐!! 기억좀 하자!
    Optional<User> findByEmail(String email);
}
