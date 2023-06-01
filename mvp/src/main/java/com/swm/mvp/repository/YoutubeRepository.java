package com.swm.mvp.repository;

import com.swm.mvp.entity.Youtube;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YoutubeRepository extends JpaRepository<Youtube, Long> {
    List<Youtube> findAllByUsers_UserId(String userId);

}
