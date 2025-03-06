package com.blog.controllers;

import com.blog.models.Post;
import com.blog.services.PostService;
import com.blog.services.CloudinaryService; // Import CloudinaryService
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CloudinaryService cloudinaryService; // Add this line

    // Helper method to create response objects
    private ResponseEntity<Map<String, Object>> createResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.status(status).body(response);
    }

    // Create a new post
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPost(
            @RequestParam("authorId") String authorId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart(value = "video", required = false) MultipartFile videoFile
    ) {
        try {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);

            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(imageFile.getBytes());
                post.setImageUrl(imageUrl);
            }

            if (videoFile != null && !videoFile.isEmpty()) {
                String videoUrl = cloudinaryService.uploadVideo(videoFile.getBytes());
                post.setVideoUrl(videoUrl);
            }

            Post createdPost = postService.createPost(authorId, post);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed!");
        }
    }


    // Get all posts
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return createResponse(HttpStatus.NO_CONTENT, "No posts available", null);
        }
        return createResponse(HttpStatus.OK, "Posts retrieved successfully", posts);
    }

    // Get posts by author ID
    @GetMapping("/author/{authorId}")
    public ResponseEntity<Map<String, Object>> getPostsByAuthorId(@PathVariable String authorId) {
        List<Post> posts = postService.getPostsByAuthorId(authorId);
        if (posts.isEmpty()) {
            return createResponse(HttpStatus.NO_CONTENT, "No posts found for this author", null);
        }
        return createResponse(HttpStatus.OK, "Posts retrieved successfully", posts);
    }

    // Get a post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable String id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return createResponse(HttpStatus.NOT_FOUND, "Post not found", null);
        }
        return createResponse(HttpStatus.OK, "Post retrieved successfully", post);
    }

    // Update a post
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable String id, @RequestBody Post updatedPost) {
        Post post = postService.updatePost(id, updatedPost);
        if (post == null) {
            return createResponse(HttpStatus.NOT_FOUND, "Post not found", null);
        }
        return createResponse(HttpStatus.OK, "Post updated successfully", post);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePost(@PathVariable String id) {
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return createResponse(HttpStatus.NOT_FOUND, "Post not found", null);
        }
        postService.deletePost(id);
        return createResponse(HttpStatus.OK, "Post deleted successfully", null);
    }
}
