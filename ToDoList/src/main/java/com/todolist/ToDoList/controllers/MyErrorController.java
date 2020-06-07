package com.todolist.ToDoList.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError() {
//do something like logging
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
