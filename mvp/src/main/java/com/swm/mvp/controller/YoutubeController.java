package com.swm.mvp.controller;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.Users;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.service.TranscriptService;
import com.swm.mvp.service.UsersService;
import com.swm.mvp.service.YoutubeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class YoutubeController {
    private final TranscriptService transcriptService;
    private final YoutubeService youtubeService;

    private final UsersService userService;

    public YoutubeController(TranscriptService transcriptService, YoutubeService youtubeService, UsersService userService) {
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
        Optional<UsersDTO> userOptional = userService.searchUser(principal.getName());
        System.out.println(principal.getName());

        if (userOptional.isPresent()) {
            UsersDTO users = userOptional.get();
            Youtube youtube = transcriptService.fetchTranscripts(youtubeId, principal.getName()).block();
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

    @GetMapping
    public List<Youtube> getAllYoutubesByUser(@PathVariable String id) {
        return youtubeService.getAllYoutubes(id);
    }

    @DeleteMapping("/{id}")
    public void deleteYoutube(@PathVariable Long id) {
        youtubeService.deleteYoutube(id);
    }

    @GetMapping("/all")
    public List<Youtube> getAllYoutubes() {
        return youtubeService.findAllYoutubes();
    }
}
