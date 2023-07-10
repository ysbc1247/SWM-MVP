package com.swm.mvp.repository;

import com.swm.mvp.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YoutubeRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByUsers_UserId(String userId);

}
