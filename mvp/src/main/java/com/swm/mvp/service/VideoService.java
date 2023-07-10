package com.swm.mvp.service;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.Video;
import com.swm.mvp.repository.UserRepository;
import com.swm.mvp.repository.YoutubeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Autowired
    private YoutubeRepository youtubeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<Video> saveYoutube(String username, Video newVideo) {
        Optional<UsersDTO> userOptional = userService.searchUser(username);
        if (userOptional.isPresent()) {
            UsersDTO users = userOptional.get();
            Video video = new Video();
            video.setLink(newVideo.getLink());
            video.setTranscriptList(newVideo.getTranscriptList());
            users.getYoutubeList().add(video);
            userService.saveUser(
                    users.userId(),
                    users.userPassword(),
                    users.roleTypes(),
                    users.email(),
                    users.nickname(),
                    users.memo(),
                    users.videoList()
            );
            youtubeRepository.save(video);
            return new ResponseEntity<>(video, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Optional<Video> findYoutubeById(Long id) {
        Optional<Video> youtube = youtubeRepository.findById(id);
        Hibernate.initialize(youtube.get().getTranscriptList());
        return youtube;
    }

    @Transactional
    public List<Video> getAllYoutubes(String userId) {
        return youtubeRepository.findAllByUsers_UserId(userId);
    }

    public void deleteYoutube(Long id) {
        youtubeRepository.deleteById(id);
    }

    public List<Video> findAllYoutubes() {
        return youtubeRepository.findAll();
    }
}
