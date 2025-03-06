package com.blog.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    @Id
    private String id;

    private String title;
    private String content;
    private User author;  // ✅ Renamed from `user` to `author`

    @Builder.Default
    private Date createdAt = new Date();

    private String imageUrl;  // Optional image URL
    private String videoUrl;  // Optional video URL
}
