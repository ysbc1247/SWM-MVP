package com.swm.mvp.service;

import com.swm.mvp.entity.Transcript;
import com.swm.mvp.entity.User;
import com.swm.mvp.entity.Video;
import com.swm.mvp.repository.UserRepository;
import com.swm.mvp.repository.YoutubeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class TranscriptService {
    private final WebClient webClient;
    private final YoutubeRepository youtubeRepository;

    private final UserRepository userRepository;
    ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
            .build();
    public TranscriptService(WebClient.Builder webClientBuilder, YoutubeRepository youtubeRepository, UserRepository userRepository) {
        this.webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl("http://localhost:5000")  // Set your base URL accordingly
                .build();
        this.youtubeRepository = youtubeRepository;
        this.userRepository = userRepository;
    }

    public Mono<Video> fetchTranscripts(String videoId, String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            return Mono.error(new RuntimeException("User not found"));
        }
        User user = userOptional.get();
        return webClient.get()
                .uri("/transcripts/" + videoId)
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .map(transcriptDataList -> {
                    Video video = new Video();
                    video.setLink("https://www.youtube.com/watch?v=" + videoId);
                    List<Transcript> transcriptList = new ArrayList<>();
                    for (Map<String, Object> transcriptData : transcriptDataList) {
                        List<Map<String, Object>> transcripts = (List<Map<String, Object>>) transcriptData.get("transcripts");
                        if (transcripts != null) {
                            for (Map<String, Object> transcriptMap : transcripts) {
                                Transcript transcript = new Transcript();
                                transcript.setSentence((String) transcriptMap.get("text"));
                                transcript.setStart(((Number) transcriptMap.get("start")).doubleValue());
                                transcript.setDuration(((Number) transcriptMap.get("duration")).doubleValue());
                                String base64Audio = (String) transcriptMap.get("audio");
                                byte[] audioBytes = null;
                                if (base64Audio != null) {
                                    audioBytes = Base64.getDecoder().decode(base64Audio);
                                }
                                transcript.setAudio(audioBytes);
                                transcript.setVideo(video);  // Setting the reference to the Youtube object
                                transcriptList.add(transcript);
                            }
                        }
                    }
                    video.setTranscriptList(transcriptList);
                    video.setUser(user);
                    return video;
                })
                .flatMap(youtube -> Mono.fromCallable(() -> youtubeRepository.save(youtube)));

    }

}
