package com.blog.services;

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
    private final BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    // Create a new user (hash the password before saving)
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash password
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // Update a user (re-hash password if updated)
    public User updateUser(String id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());

            // Only hash password if it has been updated
            if (!updatedUser.getPassword().equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            user.setRole(updatedUser.getRole()); // Update the role field
            return userRepository.save(user);
        }
        return null;
    }

    // Delete a user
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
