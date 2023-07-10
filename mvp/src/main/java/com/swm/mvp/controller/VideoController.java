package com.swm.mvp.controller;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.Video;
import com.swm.mvp.repository.YoutubeRepository;
import com.swm.mvp.service.TranscriptService;
import com.swm.mvp.service.UserService;
import com.swm.mvp.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class VideoController {
    private final TranscriptService transcriptService;
    private final VideoService videoService;
    private final UserService userService;
    private final YoutubeRepository youtubeRepository;

    public VideoController(TranscriptService transcriptService, VideoService videoService, UserService userService,
                           YoutubeRepository youtubeRepository) {
        this.transcriptService = transcriptService;
        this.videoService = videoService;
        this.userService = userService;
        this.youtubeRepository = youtubeRepository;
    }

    @GetMapping("/{id}")
    public Optional<Video> getYoutube(@PathVariable Long id) {
        return videoService.findYoutubeById(id);
    }
    @PostMapping("/save/{youtubeId}")
    public ResponseEntity<Video> saveYoutubeVideo(@PathVariable String youtubeId, Principal principal) {
        Optional<UsersDTO> userOptional = userService.searchUser(principal.getName());
        System.out.println(principal.getName());

        if (userOptional.isPresent()) {
            UsersDTO users = userOptional.get();
            Video video = transcriptService.fetchTranscripts(youtubeId, principal.getName()).block();
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
            return new ResponseEntity<>(video, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    public List<Video> getAllYoutubesByUser(@PathVariable String id) {
        return videoService.getAllYoutubes(id);
    }

    @DeleteMapping("/{id}")
    public void deleteYoutube(@PathVariable Long id) {
        videoService.deleteYoutube(id);
    }

    @GetMapping("/all")
    public List<Video> getAllYoutubes() {
        return videoService.findAllYoutubes();
    }
}
