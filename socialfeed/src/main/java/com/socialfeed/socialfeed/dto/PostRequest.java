package com.socialfeed.socialfeed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {
    
    @NotBlank(message = "내용은 필수입니다")
    private String content;
    
    private String imageUrl;
}