package com.socialfeed.socialfeed.controller;

import com.socialfeed.socialfeed.dto.FollowResponse;
import com.socialfeed.socialfeed.dto.UserResponse;
import com.socialfeed.socialfeed.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {
    
    private final FollowService followService;
    
    @PostMapping("/{followingId}")
    public ResponseEntity<FollowResponse> follow(
            @RequestHeader("X-User") String username,
            @PathVariable Long followingId) {
        FollowResponse response = followService.follow(username, followingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @DeleteMapping("/{followingId}")
    public ResponseEntity<Void> unfollow(
            @RequestHeader("X-User") String username,
            @PathVariable Long followingId) {
        followService.unfollow(username, followingId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowers(@PathVariable Long userId) {
        List<UserResponse> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }
    
    @GetMapping("/followings/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowings(@PathVariable Long userId) {
        List<UserResponse> followings = followService.getFollowings(userId);
        return ResponseEntity.ok(followings);
    }
}