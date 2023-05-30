package com.swm.mvp.controller;

import com.swm.mvp.dto.UserDTO;
import com.swm.mvp.entity.Users;
import com.swm.mvp.repository.UserRepository;
import com.swm.mvp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<Users> signUp(@RequestBody UserDTO user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        Users newUsers = new Users();
        newUsers.setUsername(user.getUsername());
        newUsers.setPassword(passwordEncoder.encode(user.getPassword()));  // encode the password

        return ResponseEntity.ok(userService.save(newUsers));
    }
}