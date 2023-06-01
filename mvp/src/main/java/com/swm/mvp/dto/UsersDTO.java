package com.swm.mvp.dto;

import com.swm.mvp.entity.RoleType;
import com.swm.mvp.entity.Users;
import com.swm.mvp.entity.Youtube;
import lombok.Getter;
import lombok.Setter;

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
        List<Youtube> youtubeList,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static UsersDTO of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube>youtubeList) {
        return UsersDTO.of(userId, userPassword, roleTypes, email, nickname, memo, youtubeList, null, null, null, null);
    }

    public static UsersDTO of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube>youtubeList, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UsersDTO(userId, userPassword, roleTypes, email, nickname, memo, youtubeList, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public List<Youtube> getYoutubeList(){
        return this.youtubeList;
    }

    public static UsersDTO from(Users entity) {
        return new UsersDTO(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getRoleTypes(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getYoutubeList(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Users toEntity() {
        return Users.of(
                userId,
                userPassword,
                roleTypes,
                email,
                nickname,
                memo,
                youtubeList
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