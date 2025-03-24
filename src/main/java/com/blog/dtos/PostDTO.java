package com.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PostDTO {
    private String id;
    private String title;
    private String content;
    private AuthorDTO author;
    private Date createdAt;
    private String imageUrl;
    private String videoUrl;
}
