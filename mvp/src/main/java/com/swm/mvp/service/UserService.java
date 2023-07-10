package com.swm.mvp.service;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.RoleType;
import com.swm.mvp.entity.User;
import com.swm.mvp.entity.Video;
import com.swm.mvp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<UsersDTO> searchUser(String username) {
        return userRepository.findByUserId(username)
                .map(UsersDTO::from);
    }

    public UsersDTO saveUser(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Video> videoList) {
        return UsersDTO.from(
                userRepository.save(User.of(username, password, roleTypes, email, nickname, memo, videoList, username))
        );
    }

    @Transactional(readOnly = true)
    public List<UsersDTO> users() {
        return userRepository.findAll().stream()
                .map(UsersDTO::from)
                .toList();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

}