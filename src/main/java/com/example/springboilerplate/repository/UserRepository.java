package com.example.springboilerplate.repository;

import com.example.springboilerplate.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByActivationDigest(String activationDigest);
    Optional<User> findByResetDigest(String resetDigest);
    
    @Query("SELECT u FROM User u WHERE u.id IN (SELECT r.followed.id FROM Relationship r WHERE r.follower.id = :userId)")
    List<User> findFollowing(@Param("userId") Long userId);
    
    @Query("SELECT u FROM User u WHERE u.id IN (SELECT r.follower.id FROM Relationship r WHERE r.followed.id = :userId)")
    List<User> findFollowers(@Param("userId") Long userId);
    
    @Query("SELECT u FROM User u WHERE u.id IN (SELECT r.followed.id FROM Relationship r WHERE r.follower.id = :userId)")
    Page<User> findFollowingPaginated(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.id IN (SELECT r.follower.id FROM Relationship r WHERE r.followed.id = :userId)")
    Page<User> findFollowersPaginated(@Param("userId") Long userId, Pageable pageable);
}
