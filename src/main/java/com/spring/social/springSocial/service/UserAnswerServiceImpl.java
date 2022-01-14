package com.spring.social.springSocial.service;

import com.spring.social.springSocial.model.UserAnswer;
import com.spring.social.springSocial.repository.UserAnswerRepository;
import com.spring.social.springSocial.service.services.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    @Autowired
    private UserAnswerRepository answerRepository;

    @Override
    public void create(UserAnswer userAnswer) {
        answerRepository.save(userAnswer);
    }

    @Override
    public List<UserAnswer> readAll() {
        return answerRepository.findAll();
    }

    @Override
    public UserAnswer read(int id) {
        return answerRepository.getById(id);
    }

    @Override
    public boolean update(UserAnswer userAnswer, int id) {
        if (answerRepository.existsById(id)) {
            userAnswer.setId(id);
            answerRepository.save(userAnswer);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (answerRepository.existsById(id)) {
            answerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAnswersByTaskId(int taskId){
        List<UserAnswer> answers = readAll();
        for (UserAnswer answer : answers) {
            if (answer.getTaskId() == taskId)
                delete(answer.getId());
        }
    }
}
