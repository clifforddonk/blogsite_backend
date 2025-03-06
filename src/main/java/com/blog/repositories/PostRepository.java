package com.blog.repositories;

import com.blog.models.Post;
import com.blog.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);// Find posts by author ID
}