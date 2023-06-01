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
public class Users extends AuditingFields {
    @Id
    @Setter
    @Column(length = 50)
    private String userId;

    @Setter @Column(nullable = false) private String userPassword;

    @Convert(converter = RoleTypesConverter.class)
    @Column(nullable = false)
    private Set<RoleType> roleTypes = new LinkedHashSet<>();


    @Setter @Column(length = 100) private String email;
    @Setter @Column(length = 100) private String nickname;
    @Setter private String memo;

    @Setter @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "users")
    private List<Youtube> youtubeList;
    public Users() {}

    private Users(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube> youtubeList, String createdBy) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.roleTypes = roleTypes;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.youtubeList = youtubeList;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    public static Users of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube> youtubeList) {
        return Users.of(userId, userPassword, roleTypes, email, nickname, memo, youtubeList, null);
    }

    public static Users of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube>youtubeList,String createdBy) {
        return new Users(userId, userPassword, roleTypes, email, nickname, memo, youtubeList, createdBy);
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
        if (!(o instanceof Users that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }

}
