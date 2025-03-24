package com.blog.services;
import com.blog.dtos.PostDTO;
import com.blog.dtos.AuthorDTO;
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

//filter post before sending to frontend
    private PostDTO mapToDTO(Post post) {
        AuthorDTO authorDTO = new AuthorDTO(post.getAuthor().getUsername());
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                authorDTO,
                post.getCreatedAt(),
                post.getImageUrl(),
                post.getVideoUrl()
        );
    }

    // Create a new post (Now requires authorId)
    public Post createPost(String authorId, Post post) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setAuthor(author); // ✅ Now it correctly references `setAuthor`
        return postRepository.save(post);
    }

    // Get all posts
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList(); // ✅ stream and convert to safe DTOs
    }

    // Get posts by author ID
    public List<PostDTO> getPostsByAuthorId(String authorId) {
        return postRepository.findByAuthorId(authorId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Get a post by ID
    public PostDTO getPostById(String id) {
        return postRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    // Update a post
    public Post updatePost(String id, Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    return postRepository.save(post);
                })
                .orElse(null);
    }

    // Delete a post
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }
}
