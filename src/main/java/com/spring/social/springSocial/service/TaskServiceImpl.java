package com.spring.social.springSocial.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.rjeschke.txtmark.Processor;
import com.spring.social.springSocial.cloudinary.MyCloudinary;
import com.spring.social.springSocial.mapper.TaskMapper;
import com.spring.social.springSocial.model.Answer;
import com.spring.social.springSocial.model.DTO.TaskDTO;
import com.spring.social.springSocial.model.Estimation;
import com.spring.social.springSocial.model.Task;
import com.spring.social.springSocial.model.UserAnswer;
import com.spring.social.springSocial.repository.TaskRepository;
import com.spring.social.springSocial.search.TaskSearch;
import com.spring.social.springSocial.service.services.EstimationService;
import com.spring.social.springSocial.service.services.TaskService;
import com.spring.social.springSocial.service.services.UserAnswerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserAnswerService answerService;
    private EstimationService estimationService;

    private final Cloudinary cloudinary = MyCloudinary.cloudinary;

    @Override
    public void create(Task task, int userId) {
        try {
            Map uploadResult1 = cloudinary.uploader().upload(task.getImg1(), ObjectUtils.emptyMap());
            task.setImg1((String) uploadResult1.get("url"));
            task.setCloudinaryId1((String) uploadResult1.get("public_id"));
            Map uploadResult2 = cloudinary.uploader().upload(task.getImg2(), ObjectUtils.emptyMap());
            task.setImg2((String) uploadResult2.get("url"));
            task.setCloudinaryId2((String) uploadResult2.get("public_id"));
            Map uploadResult3 = cloudinary.uploader().upload(task.getImg3(), ObjectUtils.emptyMap());
            task.setImg3((String) uploadResult3.get("url"));
            task.setCloudinaryId3((String) uploadResult3.get("public_id"));
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
        task.setUserId(userId);
        task.setDecide(1);
        task.setTaskCondition(Processor.process(task.getTaskCondition()));
        taskRepository.save(task);
    }

    @Override
    public List<TaskDTO> readAllDTO() {
        List<Task> tasks = readAll();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task: sortByDate(tasks)) {
            taskDTOS.add(TaskMapper.INSTANCE.taskToTaskDTO(task));
        }
        return taskDTOS;
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
            Task oldTask = read(id);
            try {
                if (!task.getImg1().startsWith("http://res.cloudinary.com/dxsv5iq47/image/upload/")){

                        cloudinary.uploader().destroy(oldTask.getCloudinaryId1(), ObjectUtils.emptyMap());

                    Map uploadResult1 = cloudinary.uploader().upload(task.getImg1(), ObjectUtils.emptyMap());
                    task.setImg1((String) uploadResult1.get("url"));
                    task.setCloudinaryId1((String) uploadResult1.get("public_id"));
                }
                else
                    task.setCloudinaryId1(oldTask.getCloudinaryId1());
                if (!task.getImg1().startsWith("http://res.cloudinary.com/dxsv5iq47/image/upload/")){
                    cloudinary.uploader().destroy(oldTask.getCloudinaryId2(), ObjectUtils.emptyMap());
                    Map uploadResult2 = cloudinary.uploader().upload(task.getImg2(), ObjectUtils.emptyMap());
                    task.setImg2((String) uploadResult2.get("url"));
                    task.setCloudinaryId2((String) uploadResult2.get("public_id"));
                }
                else
                    task.setCloudinaryId2(oldTask.getCloudinaryId2());
                if (!task.getImg1().startsWith("http://res.cloudinary.com/dxsv5iq47/image/upload/")){
                    cloudinary.uploader().destroy(oldTask.getCloudinaryId3(), ObjectUtils.emptyMap());
                    Map uploadResult3 = cloudinary.uploader().upload(task.getImg3(), ObjectUtils.emptyMap());
                    task.setImg3((String) uploadResult3.get("url"));
                    task.setCloudinaryId3((String) uploadResult3.get("public_id"));
                }
                else
                    task.setCloudinaryId3(oldTask.getCloudinaryId3());
                task.setId(id);
                task.setDecide(oldTask.getDecide());
                task.setTaskCondition(Processor.process(task.getTaskCondition()));
                taskRepository.save(task);
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (taskRepository.existsById(id)) {
            Task task = read(id);
            try {
                cloudinary.uploader().destroy(task.getCloudinaryId1(), ObjectUtils.emptyMap());
                cloudinary.uploader().destroy(task.getCloudinaryId2(), ObjectUtils.emptyMap());
                cloudinary.uploader().destroy(task.getCloudinaryId3(), ObjectUtils.emptyMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
            answerService.deleteAnswersByTaskId(id);
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
        return sortByDate(myTasks);
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
    public List<Task> setCurrentAnswers(int currentUserId, List<Task> tasks) {
        for (Task task : tasks)
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
        return sortByDate(tasks);
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

    @Override
    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        for (Task task: readAll()) {
            if (!tags.contains(task.getTag1()))
                tags.add(task.getTag1());
            if (!tags.contains(task.getTag2()))
                tags.add(task.getTag2());
            if (!tags.contains(task.getTag3()))
                tags.add(task.getTag3());
        }
        return tags;
    }

    @Override
    public List<Task> getTasksByTag(String tag) {
        List<Task> result = new ArrayList<>();
        for (Task task: readAll())
            if (task.getTag1().equals(tag) || task.getTag2().equals(tag) || task.getTag3().equals(tag))
                result.add(task);
        return sortByDate(result);
    }

    @Override
    public List<Task> setCurrentEstimations(int currentUserId, List<Task> tasks) {
        for (Task task: tasks) {
            for (Estimation estimation: estimationService.readAll()) {
                if (estimation.getUserId() == currentUserId && estimation.getTaskId() == task.getId()){
                    task.setUserEstimation(estimation.getEstimation());
                    update(task, task.getId());
                    break;
                }
                else {
                    update(task, task.getId());
                    task.setUserEstimation(0);
                }
            }
        }
        return tasks;
    }

    @Override
    public List<Task> setAVGEstimations(List<Task> tasks) {
        for (Task task: tasks) {
            task.setEstimationAVG(estimationService.getEstimationAVGForTask(task.getId()));
            update(task, task.getId());
        }
        return tasks;
    }
}
