package com.spring.social.springSocial.repository;

import com.spring.social.springSocial.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    List<UserInfo> findByEmail(@Param("email") String email);
}
