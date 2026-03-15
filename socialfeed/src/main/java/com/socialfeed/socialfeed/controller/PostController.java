package com.socialfeed.socialfeed.controller;

import com.socialfeed.socialfeed.dto.PostRequest;
import com.socialfeed.socialfeed.dto.PostResponse;
import com.socialfeed.socialfeed.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestHeader("X-User") String username,
            @Valid @RequestBody PostRequest request) {
        PostResponse response = postService.createPost(username, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    
    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed(@RequestHeader("X-User") String username) {
        List<PostResponse> feed = postService.getFeed(username);
        return ResponseEntity.ok(feed);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long userId) {
        List<PostResponse> posts = postService.getUserPosts(userId);
        return ResponseEntity.ok(posts);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @RequestHeader("X-User") String username) {
        postService.deletePost(id, username);
        return ResponseEntity.noContent().build();
    }
}