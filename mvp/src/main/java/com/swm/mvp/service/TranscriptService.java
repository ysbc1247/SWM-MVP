package com.swm.mvp.service;

import com.swm.mvp.entity.Transcript;
import com.swm.mvp.entity.Users;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.UsersRepository;
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

    private final UsersRepository usersRepository;
    ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
            .build();
    public TranscriptService(WebClient.Builder webClientBuilder, YoutubeRepository youtubeRepository, UsersRepository usersRepository) {
        this.webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl("http://localhost:5000")  // Set your base URL accordingly
                .build();
        this.youtubeRepository = youtubeRepository;
        this.usersRepository = usersRepository;
    }

    public Mono<Youtube> fetchTranscripts(String videoId, String username) {
        Optional<Users> userOptional = usersRepository.findById(username);
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
                        String base64Audio = (String) transcriptData.get("audio");
                        byte[] audioBytes = null;
                        if (base64Audio != null) {
                            audioBytes = Base64.getDecoder().decode(base64Audio);
                        }
                        transcript.setAudio(audioBytes);
                        transcriptList.add(transcript);
                    }
                    youtube.setTranscriptList(transcriptList);
                    youtube.setUsers(users);
                    return youtube;
                })
                .flatMap(youtube -> Mono.fromCallable(() -> youtubeRepository.save(youtube)));
    }

}
