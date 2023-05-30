package com.swm.mvp.repository;

import com.swm.mvp.entity.Youtube;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Flux;

public interface YoutubeRepository extends JpaRepository<Youtube, Long> {
    Flux<Youtube> findAllByUserId(Long userId);
}
