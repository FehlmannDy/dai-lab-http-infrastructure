package com.example.appdai.service;

import com.example.appdai.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> getUserWishlist(int userId) {
        return userRepository.getUserWishlist(userId);
    }

    public List<Map<String, Object>> getUserCollection(int userId) {
        return userRepository.getUserCollection(userId);
    }
}
