package com.swm.mvp.service;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.UsersRepository;
import com.swm.mvp.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YoutubeService {
    @Autowired
    private YoutubeRepository youtubeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService userService;

    public ResponseEntity<Youtube> saveYoutube(String username, Youtube newYoutube) {
        Optional<UsersDTO> userOptional = userService.searchUser(username);
        if (userOptional.isPresent()) {
            UsersDTO users = userOptional.get();
            Youtube youtube = new Youtube();
            youtube.setLink(newYoutube.getLink());
            youtube.setTranscriptList(newYoutube.getTranscriptList());
            users.getYoutubeList().add(youtube);
            userService.saveUser(
                    users.userId(),
                    users.userPassword(),
                    users.roleTypes(),
                    users.email(),
                    users.nickname(),
                    users.memo(),
                    users.youtubeList()
            );
            return new ResponseEntity<>(youtube, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public Optional<Youtube> findYoutubeById(Long id) {
        return youtubeRepository.findById(id);
    }

    public List<Youtube> getAllYoutubes(String userId) {
        return youtubeRepository.findAllByUsers_UserId(userId);
    }

    public void deleteYoutube(Long id) {
        youtubeRepository.deleteById(id);
    }

    public List<Youtube> findAllYoutubes() {
        return youtubeRepository.findAll();
    }
}
