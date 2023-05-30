package com.swm.mvp.service;

import com.swm.mvp.entity.Users;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<Youtube> saveYoutube(String username, Youtube newYoutube) {
        Optional<Users> userOptional = userService.getUserByUserName(username);
        if (userOptional.isPresent()) {
            Users users = userOptional.get();
            Youtube youtube = new Youtube();
            youtube.setLink(newYoutube.getLink());
            youtube.setTranscriptList(newYoutube.getTranscriptList());
            users.getYoutubeList().add(youtube);
            userService.saveUser(users);
            return new ResponseEntity<>(youtube, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Youtube> findYoutubeById(Long id) {
        return youtubeRepository.findById(id);
    }

    public List<Youtube> getAllYoutubes(Long userId) {
        return youtubeRepository.findAllByUsersId(userId);
    }

    public void deleteYoutube(Long id) {
        youtubeRepository.deleteById(id);
    }

    public List<Youtube> findAllYoutubes() {
        return youtubeRepository.findAll();
    }
}
