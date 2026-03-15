package com.socialfeed.socialfeed.service;

import com.socialfeed.socialfeed.dto.FollowResponse;
import com.socialfeed.socialfeed.dto.UserResponse;
import com.socialfeed.socialfeed.entity.Follow;
import com.socialfeed.socialfeed.entity.User;
import com.socialfeed.socialfeed.repository.FollowRepository;
import com.socialfeed.socialfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
    
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    
    public FollowResponse follow(String followerUsername, Long followingId) {
        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("팔로우할 사용자를 찾을 수 없습니다"));
        
        if (follower.getId().equals(following.getId())) {
            throw new RuntimeException("자기 자신을 팔로우할 수 없습니다");
        }
        
        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("이미 팔로우한 사용자입니다");
        }
        
        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        
        Follow savedFollow = followRepository.save(follow);
        
        return FollowResponse.from(savedFollow);
    }
    
    public void unfollow(String followerUsername, Long followingId) {
        User follower = userRepository.findByUsername(followerUsername)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("언팔로우할 사용자를 찾을 수 없습니다"));
        
        if (!followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("팔로우하지 않은 사용자입니다");
        }
        
        followRepository.deleteByFollowerAndFollowing(follower, following);
    }
    
    public List<UserResponse> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        
        return followRepository.findByFollowing(user)
                .stream()
                .map(follow -> UserResponse.from(follow.getFollower()))
                .collect(Collectors.toList());
    }
    
    public List<UserResponse> getFollowings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
        
        return followRepository.findByFollower(user)
                .stream()
                .map(follow -> UserResponse.from(follow.getFollowing()))
                .collect(Collectors.toList());
    }
}