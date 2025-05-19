package com.example.springboilerplate.repository;

import com.example.springboilerplate.model.Micropost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MicropostRepository extends JpaRepository<Micropost, Long> {
    List<Micropost> findByUserIdOrderByCreatedAtDesc(String userId);
    Page<Micropost> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
    int countByUserId(String userId);
    @Query("SELECT m FROM Micropost m WHERE m.user.id IN " +
           "(SELECT r.followed.id FROM Relationship r WHERE r.follower.id = :userId) " +
           "OR m.user.id = :userId ORDER BY m.createdAt DESC")
    Page<Micropost> findFeed(@Param("userId") String userId, Pageable pageable); 
}
