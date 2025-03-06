package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class BlogbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogbackendApplication.class, args);
	}

}
