package com.swm.mvp.dto;

import com.swm.mvp.entity.RoleType;
import com.swm.mvp.entity.User;
import com.swm.mvp.entity.Video;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public record UsersDTO(
        String userId,
        String userPassword,
        Set<RoleType> roleTypes,
        String email,
        String nickname,
        String memo,
        List<Video> videoList,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UsersDTO of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Video> videoList) {
        return UsersDTO.of(userId, userPassword, roleTypes, email, nickname, memo, videoList, null, null, null, null);
    }

    public static UsersDTO of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Video> videoList, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UsersDTO(userId, userPassword, roleTypes, email, nickname, memo, videoList, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public List<Video> getYoutubeList(){
        return this.videoList;
    }

    public static UsersDTO from(User entity) {
        return new UsersDTO(
                entity.getUserId(),
                entity.getPassword(),
                entity.getRoleTypes(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getVideoList(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public User toEntity() {
        return User.of(
                userId,
                userPassword,
                roleTypes,
                email,
                nickname,
                memo,
                videoList
        );
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public String userPassword() {
        return userPassword;
    }
}