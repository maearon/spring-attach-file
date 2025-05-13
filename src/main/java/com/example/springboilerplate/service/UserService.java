package com.example.springboilerplate.service;

import com.example.springboilerplate.model.User;
import com.example.springboilerplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationDigest(UUID.randomUUID().toString());
        User savedUser = userRepository.save(user);
        emailService.sendActivationEmail(savedUser);
        return savedUser;
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public boolean activate(String token) {
        Optional<User> userOpt = userRepository.findByActivationDigest(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActivated(true);
            user.setActivatedAt(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public void createPasswordReset(User user) {
        user.setResetDigest(UUID.randomUUID().toString());
        user.setResetSentAt(LocalDateTime.now());
        userRepository.save(user);
        emailService.sendPasswordResetEmail(user);
    }

    @Transactional
    public boolean resetPassword(String token, String password) {
        Optional<User> userOpt = userRepository.findByResetDigest(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Check if token is expired (2 hours)
            if (user.getResetSentAt().plusHours(2).isAfter(LocalDateTime.now())) {
                user.setPassword(passwordEncoder.encode(password));
                user.setResetDigest(null);
                user.setResetSentAt(null);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    public List<User> getFollowing(Long userId) {
        return userRepository.findFollowing(userId);
    }

    public List<User> getFollowers(Long userId) {
        return userRepository.findFollowers(userId);
    }

    public Page<User> getFollowingPaginated(Long userId, Pageable pageable) {
        return userRepository.findFollowingPaginated(userId, pageable);
    }

    public Page<User> getFollowersPaginated(Long userId, Pageable pageable) {
        return userRepository.findFollowersPaginated(userId, pageable);
    }
}
