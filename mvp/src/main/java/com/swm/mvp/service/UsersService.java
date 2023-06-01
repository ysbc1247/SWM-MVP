package com.swm.mvp.service;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.RoleType;
import com.swm.mvp.entity.Users;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public Optional<UsersDTO> searchUser(String username) {
        return usersRepository.findByUserId(username)
                .map(UsersDTO::from);
    }

    public UsersDTO saveUser(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube>youtubeList) {
        return UsersDTO.from(
                usersRepository.save(Users.of(username, password, roleTypes, email, nickname, memo, youtubeList, username))
        );
    }

    @Transactional(readOnly = true)
    public List<UsersDTO> users() {
        return usersRepository.findAll().stream()
                .map(UsersDTO::from)
                .toList();
    }

    public void deleteUser(String username) {
        usersRepository.deleteById(username);
    }

}