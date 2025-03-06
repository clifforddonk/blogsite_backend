package com.blog.services;

import com.blog.models.Post;
import com.blog.models.User;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;  // Needed to fetch the user

    // Create a new post (Now requires authorId)
    public Post createPost(String authorId, Post post) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setAuthor(author); // ✅ Now it correctly references `setAuthor`
        return postRepository.save(post);
    }

    // Get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get posts by author ID
    public List<Post> getPostsByAuthorId(String authorId) {
        return postRepository.findByAuthorId(authorId);  // Corrected query
    }

    // Get a post by ID
    public Post getPostById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    // Update a post
    public Post updatePost(String id, Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setImageUrl(updatedPost.getImageUrl());
                    post.setVideoUrl(updatedPost.getVideoUrl());
                    return postRepository.save(post);
                })
                .orElse(null);
    }

    // Delete a post
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
