package com.example.springboilerplate.service;

import com.example.springboilerplate.model.Relationship;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.repository.RelationshipRepository;
import com.example.springboilerplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final UserRepository userRepository;

    public Optional<Long> findRelationshipId(Long followerId, Long followedId) {
        return relationshipRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                                    .map(Relationship::getId);
    }

    // public boolean isFollowing(Long followerId, Long followedId) {
    //     return relationshipRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    // }

    public boolean isFollowing(Long followerId, Long followedId) {
        return findRelationshipId(followerId, followedId).isPresent();
    }

    public int countFollowing(Long userId) {
        return relationshipRepository.countByFollowerId(userId);
    }

    public int countFollowers(Long userId) {
        return relationshipRepository.countByFollowedId(userId);
    }

    @Transactional
    public void follow(Long followerId, Long followedId) {
        if (!isFollowing(followerId, followedId) && !followerId.equals(followedId)) {
            Optional<User> followerOpt = userRepository.findById(followerId);
            Optional<User> followedOpt = userRepository.findById(followedId);
            
            if (followerOpt.isPresent() && followedOpt.isPresent()) {
                Relationship relationship = new Relationship();
                relationship.setFollower(followerOpt.get());
                relationship.setFollowed(followedOpt.get());
                relationshipRepository.save(relationship);
            }
        }
    }

    @Transactional
    public void unfollow(Long followerId, Long followedId) {
        Optional<Relationship> relationshipOpt = relationshipRepository.findByFollowerIdAndFollowedId(followerId, followedId);
        relationshipOpt.ifPresent(relationshipRepository::delete);
    }
}
