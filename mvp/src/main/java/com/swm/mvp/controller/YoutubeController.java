package com.swm.mvp.controller;

import com.swm.mvp.entity.Youtube;
import com.swm.mvp.service.TranscriptService;
import com.swm.mvp.service.YoutubeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
public class YoutubeController {
    private final TranscriptService transcriptService;
    private final YoutubeService youtubeService;

    public YoutubeController(TranscriptService transcriptService, YoutubeService youtubeService) {
        this.transcriptService = transcriptService;
        this.youtubeService = youtubeService;
    }

    @GetMapping("/{id}")
    public Optional<Youtube> getYoutube(@PathVariable Long id) {
        return youtubeService.findYoutubeById(id);
    }
    @GetMapping("/transcripts/{videoId}")
    public Mono<Youtube> fetchTranscripts(@PathVariable String videoId) {
        return transcriptService.fetchTranscripts(videoId);
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
