package com.socialfeed.socialfeed.dto;

import com.socialfeed.socialfeed.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {
    
    private Long id;
    private UserResponse follower;
    private UserResponse following;
    private LocalDateTime createdAt;
    
    public static FollowResponse from(Follow follow) {
        return FollowResponse.builder()
                .id(follow.getId())
                .follower(UserResponse.from(follow.getFollower()))
                .following(UserResponse.from(follow.getFollowing()))
                .createdAt(follow.getCreatedAt())
                .build();
    }
}