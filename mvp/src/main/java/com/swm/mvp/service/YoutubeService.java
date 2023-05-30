package com.swm.mvp.service;

import com.swm.mvp.entity.User;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.UserRepository;
import com.swm.mvp.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        Optional<User> userOptional = userService.getUserByUserName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Youtube youtube = new Youtube();
            youtube.setLink(newYoutube.getLink());
            youtube.setTranscriptList(newYoutube.getTranscriptList());
            user.getYoutubeList().add(youtube);
            userService.saveUser(user);
            return new ResponseEntity<>(youtube, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Youtube> findYoutubeById(Long id) {
        return youtubeRepository.findById(id);
    }

    public Flux<Youtube> getAllYoutubes(Long userId) {
        return youtubeRepository.findAllByUserId(userId);
    }

    public void deleteYoutube(Long id) {
        youtubeRepository.deleteById(id);
    }

    public List<Youtube> findAllYoutubes() {
        return youtubeRepository.findAll();
    }
}
