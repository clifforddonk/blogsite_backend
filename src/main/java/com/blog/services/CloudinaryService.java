package com.blog.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(byte[] imageBytes) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageBytes, ObjectUtils.asMap(
                "resource_type", "image"
        ));
        return uploadResult.get("url").toString(); // Return image URL
    }

    public String uploadVideo(byte[] videoBytes) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(videoBytes, ObjectUtils.asMap(
                "resource_type", "video"
        ));
        return uploadResult.get("url").toString(); // Return video URL
    }
}
