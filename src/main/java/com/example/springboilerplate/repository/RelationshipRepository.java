package com.example.springboilerplate.repository;

import com.example.springboilerplate.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, String> {
    Optional<Relationship> findByFollowerIdAndFollowedId(String followerId, String followedId);
    int countByFollowerId(String followerId);
    int countByFollowedId(String followedId);
    boolean existsByFollowerIdAndFollowedId(String followerId, String followedId);
}
