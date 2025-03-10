package com.blog.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;

    // Define the Role enum with small letters
    public enum Role {
        user, // Default role
        admin
    }

    // Change the role field to use the enum type
    private Role role = Role.user; // Set default role to "user"
}