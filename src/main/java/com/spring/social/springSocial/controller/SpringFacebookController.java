package com.spring.social.springSocial.controller;

import com.spring.social.springSocial.model.*;
import com.spring.social.springSocial.parser.Parser;
import com.spring.social.springSocial.service.*;
import com.spring.social.springSocial.service.services.EstimationService;
import com.spring.social.springSocial.service.services.FacebookService;
import com.spring.social.springSocial.service.services.TopicService;
import com.spring.social.springSocial.service.services.UserAnswerService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class SpringFacebookController {
    @Autowired
    private FacebookService facebookService;

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private EstimationService estimationService;

    private UserInfo userInfo;
    private String currentAccessToken;
    private int currentUserId;
    private List<Task> myTasks;

    @GetMapping(value = "/facebookLogin")
    public RedirectView facebookLogin() {
        RedirectView redirectView = new RedirectView();
        String url = facebookService.facebookLogin();
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/facebook")
    public String facebook(@RequestParam(name = "code") String code) {
        String accessToken = facebookService.getFacebookAccessToken(code);
        return "redirect:/facebookProfileData/" + accessToken;
    }

    @RequestMapping(value = "/facebookProfileData/{accessToken:.+}", method = {RequestMethod.POST, RequestMethod.GET})
    public String facebookProfileData(@PathVariable String accessToken, Model model) {
        currentAccessToken = accessToken;
        User user = facebookService.getFacebookUserProfile(currentAccessToken);
        userInfo = new UserInfo(user.getFirstName(), user.getEmail());
        userInfoService.create(userInfo);
        currentUserId = userInfoService.findByEmail(userInfo.getEmail()).get(0).getId();
        if(user.getEmail().equals("chednik2002@yandex.ru"))
            return "redirect:/admin";
        List<Task> allTasks = taskService.setCurrentAnswers(currentUserId, taskService.readAll());
        taskService.setCurrentEstimations(currentUserId, allTasks);
        taskService.setAVGEstimations(allTasks);
        model.addAttribute("allTasks", allTasks);
        model.addAttribute("user", userInfo);
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("answer", new Answer());
        model.addAttribute("tags", taskService.getTags());
        model.addAttribute("estimation", new Estimation());
        return "view/userprofile";
    }

    @GetMapping(value = "/personalAccount/")
    public String personalAccount(Model model) {
        currentUserId = userInfoService.findByEmail(userInfo.getEmail()).get(0).getId();
        myTasks = taskService.getMyTasks(currentUserId);
        model.addAttribute("myTasks", myTasks);
        model.addAttribute("user", userInfo);
        model.addAttribute("accessToken", currentAccessToken);
        model.addAttribute("topics", topicService.readAll());
        model.addAttribute("task", new Task());
        model.addAttribute("correctAnswers", taskService.correctAnswers() + " " + Parser.taskParser(taskService.correctAnswers()));
        model.addAttribute("incorrectAnswers", taskService.incorrectAnswers() + " " + Parser.taskParser(taskService.incorrectAnswers()));
        model.addAttribute("numberCreatedTasks", myTasks.size() + " " + Parser.taskParser(myTasks.size()));
        return "view/personalAccount";
    }

    @PostMapping("/createTask/")
    public String createTask(@ModelAttribute Task task) {
        try {
            taskService.create(task, currentUserId);
        }
        catch (Exception e){
            return "view/error";
        }
        return "redirect:/personalAccount/";
    }

    @RequestMapping("/checkAnswer/")
    public String checkAnswer(@ModelAttribute Answer answer) {
        if (taskService.checkAnswer(answer)) {
            userAnswerService.create(new UserAnswer(currentUserId, answer.getTaskId(), 2));
            Task task = taskService.read(answer.getTaskId());
            task.setDecide(2);
            taskService.update(task, answer.getTaskId());
            return "redirect:/facebookProfileData/" + currentAccessToken;
        }
        userAnswerService.create(new UserAnswer(currentUserId, answer.getTaskId(), 3));
        Task task = taskService.read(answer.getTaskId());
        task.setDecide(3);
        taskService.update(task, answer.getTaskId());
        return "redirect:/facebookProfileData/" + currentAccessToken;
    }

    @RequestMapping("/updateTask/{taskId}")
    public String updateTask(@ModelAttribute Task task, @PathVariable String taskId) {
        try {
            task.setUserId(currentUserId);
            taskService.update(task, Integer.parseInt(taskId));
        }
        catch (Exception e){
            return "view/error";
        }
        return "redirect:/personalAccount/";
    }

    @RequestMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable String taskId){
        taskService.delete(Integer.parseInt(taskId));
        return "redirect:/personalAccount/";
    }

    @RequestMapping("/searchBy/{tag}")
    public String searchByTag(@PathVariable String tag, Model model){
        List<Task> tasks = taskService.getTasksByTag(tag);
        tasks = taskService.setCurrentAnswers(currentUserId, tasks);
        model.addAttribute("allTasks", tasks);
        model.addAttribute("user", userInfo);
        model.addAttribute("accessToken", currentAccessToken);
        model.addAttribute("answer", new Answer());
        model.addAttribute("tags", taskService.getTags());
        model.addAttribute("estimation", new Estimation());
        return "view/userprofile";
    }

    @RequestMapping("/rate/{taskId}")
    public String rate(@PathVariable String taskId, @ModelAttribute Estimation estimation){
        estimationService.create(estimation, currentUserId, Integer.parseInt(taskId));
        return "redirect:/facebookProfileData/" + currentAccessToken;
    }
}
