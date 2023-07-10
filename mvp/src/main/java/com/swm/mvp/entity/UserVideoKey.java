package com.swm.mvp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UserVideoKey implements Serializable {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "video_id")
    private Long videoId;

    // getters and setters

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserVideoKey)) return false;
        UserVideoKey that = (UserVideoKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, videoId);
    }
}
