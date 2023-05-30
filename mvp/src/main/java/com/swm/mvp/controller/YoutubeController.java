package com.swm.mvp.controller;

import com.swm.mvp.entity.User;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.service.CustomUserDetails;
import com.swm.mvp.service.TranscriptService;
import com.swm.mvp.service.UserService;
import com.swm.mvp.service.YoutubeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class YoutubeController {
    private final TranscriptService transcriptService;
    private final YoutubeService youtubeService;

    private final UserService userService;

    public YoutubeController(TranscriptService transcriptService, YoutubeService youtubeService, UserService userService) {
        this.transcriptService = transcriptService;
        this.youtubeService = youtubeService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Optional<Youtube> getYoutube(@PathVariable Long id) {
        return youtubeService.findYoutubeById(id);
    }
    @PostMapping("/save/{youtubeId}")
    public ResponseEntity<Youtube> saveYoutubeVideo(@PathVariable String youtubeId, Principal principal) {
        Optional<User> userOptional = userService.getUserByUserName(principal.getName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Youtube youtube = transcriptService.fetchTranscripts(youtubeId).block();
            user.getYoutubeList().add(youtube);
            userService.saveUser(user);
            return new ResponseEntity<>(youtube, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Flux<Youtube> getAllYoutubesByUser(Principal principal) {
        Long userId = ((CustomUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();
        return youtubeService.getAllYoutubes(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteYoutube(@PathVariable Long id) {
        youtubeService.deleteYoutube(id);
    }

    @GetMapping
    public List<Youtube> getAllYoutubes() {
        return youtubeService.findAllYoutubes();
    }
}
