package com.swm.mvp.config;
import com.swm.mvp.entity.RoleType;
import com.swm.mvp.service.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] rolesAboveManager = {RoleType.MANAGER.name(), RoleType.DEVELOPER.name(), RoleType.ADMIN.name()};

        return http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers(HttpMethod.POST, "/**").hasAnyRole(rolesAboveManager)
                        .mvcMatchers(HttpMethod.GET, "/**").hasAnyRole(rolesAboveManager)
                        .mvcMatchers(HttpMethod.DELETE, "/**").hasAnyRole(rolesAboveManager)
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> userService
                .searchUser(username)
                .map(BoardUsersPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}