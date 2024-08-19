package com.ojasare.notes.services;

import com.ojasare.notes.dtos.UserDTO;
import com.ojasare.notes.models.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDTO getUserById(Long id);

    User findByUsername(String username);
}
