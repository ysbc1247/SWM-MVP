package com.swm.mvp.controller;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.User;
import com.swm.mvp.repository.UserRepository;
import com.swm.mvp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @PostMapping("/signup")
    public ResponseEntity<UsersDTO> signUp(@RequestBody UsersDTO user) {

        if (userRepository.findByUserId(user.toEntity().getUserId()).isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        User newUser = new User();
        newUser.setUserId(user.toEntity().getUserId());
        newUser.setPassword(passwordEncoder.encode(user.toEntity().getPassword()));  // encode the password

        return ResponseEntity.ok(userService.saveUser( user.userId(),
                user.userPassword(),
                user.roleTypes(),
                user.email(),
                user.nickname(),
                user.memo(),
                user.videoList()
        ));
    }
}