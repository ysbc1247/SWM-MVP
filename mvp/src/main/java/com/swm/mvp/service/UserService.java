package com.swm.mvp.service;

import com.swm.mvp.entity.Users;
import com.swm.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users save(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return userRepository.save(users);
    }
    public Optional<Users> getUserByUserId(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Users> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(Users users) {
    }
}

