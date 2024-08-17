package com.ojasare.notes.repositories;

import com.ojasare.notes.models.AppRole;
import com.ojasare.notes.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
