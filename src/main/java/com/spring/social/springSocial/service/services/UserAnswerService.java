package com.spring.social.springSocial.service.services;

import com.spring.social.springSocial.model.UserAnswer;

import java.util.List;

public interface UserAnswerService {
    void create(UserAnswer userAnswer);

    List<UserAnswer> readAll();

    UserAnswer read(int id);

    boolean update(UserAnswer userAnswer, int id);

    boolean delete(int id);

    public void deleteAnswersByTaskId(int taskId);
}
