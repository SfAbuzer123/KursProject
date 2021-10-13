package com.spring.social.springSocial.controller;

import com.spring.social.springSocial.model.Answer;
import com.spring.social.springSocial.model.Task;
import com.spring.social.springSocial.model.UserAnswer;
import com.spring.social.springSocial.model.UserInfo;
import com.spring.social.springSocial.parser.Parser;
import com.spring.social.springSocial.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.Comparator;
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
    public String facebook(@RequestParam(required = false, name = "code") String code) {
        String accessToken = facebookService.getFacebookAccessToken(code);
        return "redirect:/facebookProfileData/" + accessToken;
    }

    @RequestMapping(value = "/facebookProfileData/{accessToken:.+}", method = {RequestMethod.POST, RequestMethod.GET})
    public String facebookProfileData(@PathVariable String accessToken, Model model) {
        currentAccessToken = accessToken;
        User user = facebookService.getFacebookUserProfile(currentAccessToken);
        userInfo = new UserInfo(user.getFirstName(), user.getEmail());
        if (!userInfoService.readAll().contains(userInfo))
            userInfoService.create(userInfo);
        currentUserId = userInfoService.findByEmail(userInfo.getEmail()).get(0).getId();
        if(user.getEmail().equals("chednik2002@yandex.ru"))
            return "redirect:/admin";
        List<Task> allTasks = taskService.setCurrentAnswers(currentUserId);
        allTasks = taskService.sortByDate(allTasks);
        model.addAttribute("allTasks", allTasks);
        model.addAttribute("user", userInfo);
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("answer", new Answer());
        return "view/userprofile";
    }

    @GetMapping(value = "/personalAccount/")
    public String personalAccount(Model model) {
        currentUserId = userInfoService.findByEmail(userInfo.getEmail()).get(0).getId();
        myTasks = taskService.getMyTasks(currentUserId);
        myTasks = taskService.sortByDate(myTasks);
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
        taskService.create(task, currentUserId);
        return "redirect:/personalAccount/";
    }

    @RequestMapping("/checkAnswer/")
    public String checkAnswer(@ModelAttribute Answer answer) {
        if (taskService.checkAnswer(answer)) {
            userAnswerService.create(new UserAnswer(currentUserId, answer.getTaskId(), 2));
            Task task = taskService.read(answer.getTaskId());
            task.setDecide(2);
            taskService.update(task, answer.getTaskId());
            return "redirect:/facebookLogin";
        }
        userAnswerService.create(new UserAnswer(currentUserId, answer.getTaskId(), 3));
        Task task = taskService.read(answer.getTaskId());
        task.setDecide(3);
        taskService.update(task, answer.getTaskId());
        return "redirect:/facebookLogin";
    }

    @RequestMapping("/updateTask/{taskId}")
    public String updateTask(@ModelAttribute Task task, @PathVariable String taskId) {
        task.setUserId(currentUserId);
        taskService.update(task, Integer.parseInt(taskId));
        return "redirect:/personalAccount/";
    }

    @RequestMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable String taskId){
        taskService.delete(Integer.parseInt(taskId));
        return "redirect:/personalAccount/";
    }

}
