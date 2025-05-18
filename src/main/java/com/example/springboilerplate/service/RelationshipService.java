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

    public Optional<Long> findRelationshipId(String followerId, String followedId) {
        return relationshipRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                                    .map(Relationship::getId);
    }

    public boolean isFollowing(String followerId, String followedId) {
        return findRelationshipId(followerId, followedId).isPresent();
    }

    public int countFollowing(String userId) {
        return relationshipRepository.countByFollowerId(userId);
    }

    public int countFollowers(String userId) {
        return relationshipRepository.countByFollowedId(userId);
    }

    @Transactional
    public void follow(String followerId, String followedId) {
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
    public void unfollow(String followerId, String followedId) {
        Optional<Relationship> relationshipOpt = relationshipRepository.findByFollowerIdAndFollowedId(followerId, followedId);
        relationshipOpt.ifPresent(relationshipRepository::delete);
    }
}
