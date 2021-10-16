package com.spring.social.springSocial.controller;

import com.spring.social.springSocial.model.SearchObject;
import com.spring.social.springSocial.model.Task;
import com.spring.social.springSocial.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/")
    public String home(Model model){
        List<Task> tasks = taskService.readAll();
        tasks = taskService.sortByDate(tasks);
        model.addAttribute("searchObject", new SearchObject());
        model.addAttribute("tasks", tasks);
        model.addAttribute("tags", taskService.getTags());
        return "view/home";
    }

    @RequestMapping("/search/{text}")
    public String search(@PathVariable String text, Model model){
        model.addAttribute("allTasks", taskService.search(text));
        taskService.search(text).stream().peek(System.out::println);
        return "view/home";
    }

    @RequestMapping(value = "/{tag}")
    public String searchByTag(@PathVariable String tag, Model model){
        model.addAttribute("tasks", taskService.getTasksByTag(tag));
        model.addAttribute("tags", taskService.getTags());
        return "view/home";
    }
}
