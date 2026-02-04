package com.socialfeed.socialfeed.repository;

import com.socialfeed.socialfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // username으로 사용자 찾기
    Optional<User> findByUsername(String username);
    
    // email로 사용자 찾기
    Optional<User> findByEmail(String email);
    
    // username 존재 여부 확인
    boolean existsByUsername(String username);
    
    // email 존재 여부 확인
    boolean existsByEmail(String email);
}