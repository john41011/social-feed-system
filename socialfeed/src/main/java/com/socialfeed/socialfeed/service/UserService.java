package com.socialfeed.socialfeed.service;

import com.socialfeed.socialfeed.dto.SignupRequest;
import com.socialfeed.socialfeed.dto.UserResponse;
import com.socialfeed.socialfeed.entity.User;
import com.socialfeed.socialfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다");
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .bio(request.getBio())
                .build();
        
        User savedUser = userRepository.save(user);
        
        return UserResponse.from(savedUser);
    }
}