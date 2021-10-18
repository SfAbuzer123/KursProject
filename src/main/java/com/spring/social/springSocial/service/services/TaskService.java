package com.spring.social.springSocial.service.services;

import com.spring.social.springSocial.model.Answer;
import com.spring.social.springSocial.model.DTO.TaskDTO;
import com.spring.social.springSocial.model.Task;

import java.util.List;

public interface TaskService {
    void create(Task task, int userId);

    List<TaskDTO> readAllDTO();

    List<Task> readAll();

    Task read(int id);

    boolean update(Task task, int id);

    boolean delete(int id);

    List<Task> getMyTasks(int id);

    int correctAnswers();

    int incorrectAnswers();

    List<Task> setCurrentAnswers(int currentUserId, List<Task> tasks);

    List<Task> search(String text);

    boolean checkAnswer(Answer answer);

    List<Task> sortByDate(List<Task> tasks);

    List<String> getTags();

    List<Task> getTasksByTag(String tag);

    List<Task> setCurrentEstimations(int currentUserId, List<Task> tasks);

    List<Task> setAVGEstimations(List<Task> tasks);
}
