package com.swm.mvp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserVideo {

    @EmbeddedId
    private UserVideoKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("videoId")
    @JoinColumn(name = "video_id")
    private Video video;

}
