package com.blog.services;

import com.blog.dtos.UserDTO;
import com.blog.models.User;
import com.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Map User to UserDTO (no password leak)
    public UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    // Create a new user with hashed password
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Get all users (returning DTOs)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserDTO)
                .toList();
    }

    // Get user by ID
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // Update username only
    public User updateUser(String id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            return userRepository.save(user);
        }
        return null;
    }

    // Delete user by ID
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
