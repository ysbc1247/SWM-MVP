package com.swm.mvp.entity;

import com.swm.mvp.entity.converter.RoleTypesConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Getter
@ToString(callSuper = true)
@Table
@Entity
public class User extends AuditingFields {
    @Id
    @Setter
    @Column(length = 50)
    private String userId;

    @Setter @Column(nullable = false) private String password;

    @Convert(converter = RoleTypesConverter.class)
    @Column(nullable = false)
    private Set<RoleType> roleTypes = new LinkedHashSet<>();


    @Setter @Column(length = 100) private String email;
    @Setter @Column(length = 100) private String nickname;
    @Setter private String memo;

    @OneToMany(mappedBy = "user")
    private List<UserVideo> userVideos;

    public User() {}

    private User(String userId, String password, Set<RoleType> roleTypes, String email, String nickname, String memo, List<UserVideo> userVideos, String createdBy) {
        this.userId = userId;
        this.password = password;
        this.roleTypes = roleTypes;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.userVideos = userVideos;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static User of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<UserVideo> userVideos) {
        return User.of(userId, userPassword, roleTypes, email, nickname, memo, userVideos, null);
    }

    public static User of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<UserVideo> userVideos, String createdBy) {
        return new User(userId, userPassword, roleTypes, email, nickname, memo, userVideos, createdBy);
    }

    public void addRoleType(RoleType roleType) {
        this.getRoleTypes().add(roleType);
    }

    public void addRoleTypes(Collection<RoleType> roleTypes) {
        this.getRoleTypes().addAll(roleTypes);
    }

    public void removeRoleType(RoleType roleType) {
        this.getRoleTypes().remove(roleType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }

}
