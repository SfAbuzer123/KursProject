package com.spring.social.springSocial.controller;

import com.spring.social.springSocial.model.Task;
import com.spring.social.springSocial.parser.Parser;
import com.spring.social.springSocial.service.TaskService;
import com.spring.social.springSocial.service.TopicService;
import com.spring.social.springSocial.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TopicService topicService;

    private int currentUserId;

    @RequestMapping("admin")
    public String admin(Model model){
        model.addAttribute("users", userInfoService.readAll());
        return "view/admin";
    }

    @RequestMapping("/userAdminProfile/{id}")
    public String userAdminProfile(@PathVariable String id, Model model){
        currentUserId = Integer.parseInt(id);
        List<Task> myTasks = taskService.getMyTasks(currentUserId);
        model.addAttribute("myTasks", myTasks);
        model.addAttribute("topics", topicService.readAll());
        model.addAttribute("task", new Task());
        model.addAttribute("correctAnswers", taskService.correctAnswers());
        model.addAttribute("correctAnswers", taskService.correctAnswers() + " " + Parser.taskParser(taskService.correctAnswers()));
        model.addAttribute("incorrectAnswers", taskService.incorrectAnswers() + " " + Parser.taskParser(taskService.incorrectAnswers()));
        model.addAttribute("numberCreatedTasks", myTasks.size() + " " + Parser.taskParser(myTasks.size()));
        model.addAttribute("user", userInfoService.read(currentUserId));
        return "view/userAdminProfile";
    }

    @RequestMapping("/createTaskByAdmin/")
    public String createTaskByAdmin(@ModelAttribute Task task){
        taskService.create(task, currentUserId);
        return "redirect:/userAdminProfile/"+currentUserId;
    }

    @RequestMapping("/updateTaskByAdmin/{taskId}")
    public String updateTaskByAdmin(@PathVariable String taskId, @ModelAttribute Task task){
        task.setUserId(currentUserId);
        taskService.update(task, Integer.parseInt(taskId));
        return "redirect:/userAdminProfile/"+currentUserId;
    }
}
