package com.spring.social.springSocial.service.services;

import com.spring.social.springSocial.model.UserInfo;

import java.util.List;

public interface UserInfoService {

    void create(UserInfo userInfo);

    List<UserInfo> readAll();

    UserInfo read(int id);

    boolean update(UserInfo userInfo, int id);

    boolean delete(int id);

    List<UserInfo> findByEmail(String email);
}
