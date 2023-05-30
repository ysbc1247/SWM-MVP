package com.swm.mvp.service;

import com.swm.mvp.entity.User;
import com.swm.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByUserId(Long id) {
        return userRepository.findById(id);
    }

    public void saveUser(User user) {
    }
}

