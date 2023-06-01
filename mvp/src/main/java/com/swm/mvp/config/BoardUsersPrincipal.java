package com.swm.mvp.config;

import com.swm.mvp.dto.UsersDTO;
import com.swm.mvp.entity.RoleType;
import com.swm.mvp.entity.Youtube;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardUsersPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo,
        List<Youtube> youtubeList,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static BoardUsersPrincipal of(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube>youtubeList) {
        return BoardUsersPrincipal.of(username, password, roleTypes, email, nickname, memo, youtubeList, Map.of());
    }

    public static BoardUsersPrincipal of(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo, List<Youtube>youtubeList, Map<String, Object> oAuth2Attributes) {
        return new BoardUsersPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getRoleName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                memo,
                youtubeList,
                oAuth2Attributes
        );
    }

    public static BoardUsersPrincipal from(UsersDTO dto) {
        return BoardUsersPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.roleTypes(),
                dto.email(),
                dto.nickname(),
                dto.memo(),
                dto.youtubeList()
        );
    }

    public UsersDTO toDto() {
        return UsersDTO.of(
                username,
                password,
                authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(RoleType::valueOf)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                memo,
                youtubeList
        );
    }


    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    @Override public Map<String, Object> getAttributes() { return oAuth2Attributes; }
    @Override public String getName() { return username; }

}