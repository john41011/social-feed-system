package com.socialfeed.socialfeed.dto;

import com.socialfeed.socialfeed.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    
    private Long id;
    private String content;
    private String imageUrl;
    private UserResponse user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .user(UserResponse.from(post.getUser()))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}