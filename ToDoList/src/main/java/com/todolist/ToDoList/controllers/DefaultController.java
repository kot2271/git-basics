package com.todolist.ToDoList.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("title", "Список дел");
    return "home";
  }

  @GetMapping("/search")
  public String search(Model model) {
    model.addAttribute("title", "Поиск дел");
    return "search";
  }
}
