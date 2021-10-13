package com.spring.social.springSocial.service;

import com.spring.social.springSocial.model.UserInfo;
import com.spring.social.springSocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(UserInfo userInfo) {
        userRepository.save(userInfo);
    }

    @Override
    public List<UserInfo> readAll() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo read(int id) {
        return userRepository.getById(id);
    }

    @Override
    public boolean update(UserInfo userInfo, int id) {
        if (userRepository.existsById(id)) {
            userInfo.setId(id);
            userRepository.save(userInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<UserInfo> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
