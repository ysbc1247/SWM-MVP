package com.swm.mvp.repository;

import com.swm.mvp.entity.Youtube;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface YoutubeRepository extends JpaRepository<Youtube, Long> {
    List<Youtube> findAllByUsersId(Long userId);
}
