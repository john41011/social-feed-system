package com.socialfeed.socialfeed.service;

import com.socialfeed.socialfeed.dto.PostRequest;
import com.socialfeed.socialfeed.dto.PostResponse;
import com.socialfeed.socialfeed.entity.Post;
import com.socialfeed.socialfeed.entity.User;
import com.socialfeed.socialfeed.repository.PostRepository;
import com.socialfeed.socialfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    public PostResponse createPost(String username, PostRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        
        Post post = Post.builder()
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .user(user)
                .build();
        
        Post savedPost = postRepository.save(post);
        
        return PostResponse.from(savedPost);
    }
    
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
    
    public List<PostResponse> getUserPosts(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }
    
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        return PostResponse.from(post);
    }
    
    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));
        
        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("게시글 삭제 권한이 없습니다");
        }
        
        postRepository.delete(post);
    }
}