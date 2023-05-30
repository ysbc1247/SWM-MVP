package com.swm.mvp.service;

import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.YoutubeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class YoutubeService {
    private final YoutubeRepository youtubeRepository;

    public YoutubeService(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    public Youtube saveYoutube(Youtube youtube) {
        return youtubeRepository.save(youtube);
    }

    public Optional<Youtube> findYoutubeById(Long id) {
        return youtubeRepository.findById(id);
    }


    public void deleteYoutube(Long id) {
        youtubeRepository.deleteById(id);
    }

    public List<Youtube> findAllYoutubes() {
        return youtubeRepository.findAll();
    }
}
