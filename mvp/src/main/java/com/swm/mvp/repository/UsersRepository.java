package com.swm.mvp.repository;

import com.swm.mvp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUserId(String id);
}
