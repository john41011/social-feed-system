package com.socialfeed.socialfeed.repository;

import com.socialfeed.socialfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<Post> findAllByOrderByCreatedAtDesc();
    
    @Query("SELECT p FROM Post p WHERE p.user.id IN " +
           "(SELECT f.following.id FROM Follow f WHERE f.follower.id = :userId) " +
           "ORDER BY p.createdAt DESC")
    List<Post> findFeedByUserId(@Param("userId") Long userId);
}