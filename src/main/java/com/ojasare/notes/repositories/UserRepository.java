package com.ojasare.notes.repositories;

import com.ojasare.notes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUserName(String username);
}
