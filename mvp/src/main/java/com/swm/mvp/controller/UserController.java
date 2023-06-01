package com.swm.mvp.controller;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.Users;
import com.swm.mvp.repository.UsersRepository;
import com.swm.mvp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UsersService userService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }
    @PostMapping("/signup")
    public ResponseEntity<UsersDTO> signUp(@RequestBody UsersDTO user) {

        if (usersRepository.findByUserId(user.toEntity().getUserId()).isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        Users newUsers = new Users();
        newUsers.setUserId(user.toEntity().getUserId());
        newUsers.setUserPassword(passwordEncoder.encode(user.toEntity().getUserPassword()));  // encode the password

        return ResponseEntity.ok(userService.saveUser( user.userId(),
                user.userPassword(),
                user.roleTypes(),
                user.email(),
                user.nickname(),
                user.memo(),
                user.youtubeList()
        ));
    }
}