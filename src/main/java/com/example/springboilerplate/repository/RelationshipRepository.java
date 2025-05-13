package com.example.springboilerplate.repository;

import com.example.springboilerplate.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    Optional<Relationship> findByFollowerIdAndFollowedId(Long followerId, Long followedId);
    int countByFollowerId(Long followerId);
    int countByFollowedId(Long followedId);
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);
}
