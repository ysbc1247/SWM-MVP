package com.swm.mvp.service;

import com.swm.mvp.entity.Transcript;
import com.swm.mvp.entity.Users;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.UserRepository;
import com.swm.mvp.repository.YoutubeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TranscriptService {
    private final WebClient webClient;
    private final YoutubeRepository youtubeRepository;

    private final UserRepository userRepository;

    public TranscriptService(WebClient.Builder webClientBuilder, YoutubeRepository youtubeRepository, UserRepository userRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
        this.youtubeRepository = youtubeRepository;
        this.userRepository = userRepository;
    }

    public Mono<Youtube> fetchTranscripts(String videoId, String username) {
        Optional<Users> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return Mono.error(new RuntimeException("User not found"));
        }
        Users users = userOptional.get();
        return webClient.get()
                .uri("/transcripts/" + videoId)
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .map(transcriptDataList -> {
                    Youtube youtube = new Youtube();
                    youtube.setLink("https://www.youtube.com/watch?v=" + videoId);

                    List<Transcript> transcriptList = new ArrayList<>();
                    for (Map<String, Object> transcriptData : transcriptDataList) {
                        Transcript transcript = new Transcript();
                        transcript.setText((String) transcriptData.get("text"));
                        transcript.setStart((Double) transcriptData.get("start"));
                        transcript.setDuration((Double) transcriptData.get("duration"));

                        transcriptList.add(transcript);
                    }
                    youtube.setTranscriptList(transcriptList);
                    youtube.setUsers(users);
                    return youtube;
                })
                .flatMap(youtube -> Mono.fromCallable(() -> youtubeRepository.save(youtube)));
    }

}
