package com.example.springboilerplate.repository;

import com.example.springboilerplate.model.Micropost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MicropostRepository extends JpaRepository<Micropost, Long> {
    List<Micropost> findByUserIdOrderByCreatedAtDesc(Long userId);
    Page<Micropost> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    @Query("SELECT m FROM Micropost m WHERE m.user.id IN " +
           "(SELECT r.followed.id FROM Relationship r WHERE r.follower.id = :userId) " +
           "OR m.user.id = :userId ORDER BY m.createdAt DESC")
    Page<Micropost> findFeed(@Param("userId") Long userId, Pageable pageable);
}
