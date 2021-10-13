package com.spring.social.springSocial.controller;

import com.spring.social.springSocial.model.SearchObject;
import com.spring.social.springSocial.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/")
    public String home(Model model){
        model.addAttribute("searchObject", new SearchObject());
        model.addAttribute("allTasks", taskService.readAll());
        return "view/home";
    }

    @RequestMapping("/search/{text}")
    public String search(@PathVariable String text, Model model){
        model.addAttribute("allTasks", taskService.search(text));
        taskService.search(text).stream().peek(System.out::println);
        return "view/home";
    }
}
