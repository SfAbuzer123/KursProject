package com.spring.social.springSocial.service;

import com.spring.social.springSocial.model.Answer;
import com.spring.social.springSocial.model.Task;
import com.spring.social.springSocial.model.UserAnswer;
import com.spring.social.springSocial.repository.TaskRepository;
import com.spring.social.springSocial.search.TaskSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserAnswerService answerService;

    @Override
    public void create(Task task, int userId) {
        task.setUserId(userId);
        task.setDecide(1);
        taskRepository.save(task);
    }

    @Override
    public List<Task> readAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task read(int id) {
        return taskRepository.getById(id);
    }

    @Override
    public boolean update(Task task, int id) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            task.setDecide(task.getDecide());
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Task> getMyTasks(int id) {
        List<Task> myTasks = new ArrayList<>();
        for (Task task: taskRepository.findAll())
            if (task.getUserId() == id)
                myTasks.add(task);
        return myTasks;
    }

    @Override
    public int correctAnswers() {
        int count = 0;
        for (Task task: taskRepository.findAll())
            if (task.getDecide() == 2)
                count++;
        return count;
    }

    @Override
    public int incorrectAnswers() {
        int count = 0;
        for (Task task: taskRepository.findAll())
            if (task.getDecide() == 3)
                count++;
        return count;
    }

    @Override
    public List<Task> setCurrentAnswers(int currentUserId) {
        for (Task task : readAll())
            for (UserAnswer userAnswer : answerService.readAll()
            ) {
                if (userAnswer.getUserId() == currentUserId && task.getId() == userAnswer.getTaskId()) {
                    task.setDecide(userAnswer.getDecided());
                    update(task, task.getId());
                    break;
                } else {
                    update(task, task.getId());
                    task.setDecide(1);
                }
            }
        return readAll();
    }

    @Override
    public boolean checkAnswer(Answer answer) {
        return answer.getAnswer().equals(read(answer.getTaskId()).getRightAnswer1()) ||
                answer.getAnswer().equals(read(answer.getTaskId()).getRightAnswer2()) ||
                answer.getAnswer().equals(read(answer.getTaskId()).getRightAnswer3());
    }

    @Override
    public List<Task> search(String text) {
        return new TaskSearch().search(text);
    }

    @Override
    public List<Task> sortByDate(List<Task> tasks) {
        tasks.sort((Task o1, Task o2) -> o2.getId() - o1.getId());
        return tasks;
    }
}
