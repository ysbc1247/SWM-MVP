package com.swm.mvp.service;

import com.swm.mvp.entity.Transcript;
import com.swm.mvp.entity.Youtube;
import com.swm.mvp.repository.YoutubeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TranscriptService {
    private final WebClient webClient;
    private final YoutubeRepository youtubeRepository;

    public TranscriptService(WebClient.Builder webClientBuilder, YoutubeRepository youtubeRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
        this.youtubeRepository = youtubeRepository;
    }

    public Mono<Youtube> fetchTranscripts(String videoId) {
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

                    return youtube;
                })
                .flatMap(youtube -> Mono.fromCallable(() -> youtubeRepository.save(youtube)));
    }

}
