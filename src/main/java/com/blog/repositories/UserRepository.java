package com.blog.repositories;

import com.blog.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email); // Find user by email
    Optional<User> findByUsername(String username); // Find user by username
}