package com.todolist.ToDoList.controllers;

import com.todolist.ToDoList.Repository.TaskRepository;
import com.todolist.ToDoList.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

  @Autowired private TaskRepository taskRepository;

  @GetMapping("/tasks")
  public String taskMain(Model model) {
    Iterable<Task> tasks = taskRepository.findAll();
    model.addAttribute("tasks", tasks);
    return "taskMain";
  }

  @GetMapping("/tasks/add")
  public String taskAdd(Model model) {
    return "taskAdd";
  }

  @PostMapping("/tasks/add")
  public String taskPostAdd(
      @RequestParam String title,
      @RequestParam String description,
      @RequestParam String full_text,
      Model model) {
    Task task = new Task(title, description, full_text);
    taskRepository.save(task);
    return "redirect:/tasks";
  }

  @GetMapping("/tasks/{id}")
  public String taskDetails(@PathVariable(value = "id") int id, Model model) {
    if (!taskRepository.existsById(id)) {
      return "redirect:/tasks";
    }
    Optional<Task> task = taskRepository.findById(id);
    ArrayList<Task> result = new ArrayList<>();
    task.ifPresent(result::add);
    model.addAttribute("tasks", result);
    return "taskDetails";
  }

  @GetMapping("/tasks/{id}/edit")
  public String taskEdit(@PathVariable(value = "id") int id, Model model) {
    if (!taskRepository.existsById(id)) {
      return "redirect:/tasks";
    }
    Optional<Task> task = taskRepository.findById(id);
    ArrayList<Task> result = new ArrayList<>();
    task.ifPresent(result::add);
    model.addAttribute("tasks", result);
    return "taskEdit";
  }

  @PostMapping("/tasks/{id}/edit")
  public String taskPostUpdate(
      @PathVariable(value = "id") int id,
      @RequestParam String title,
      @RequestParam String description,
      @RequestParam String full_text,
      Model model) {
    Task task = taskRepository.findById(id).orElseThrow();
    task.setTitle(title);
    task.setDescription(description);
    task.setFull_text(full_text);
    taskRepository.save(task);
    return "redirect:/tasks";
  }

  @PostMapping("/tasks/{id}/remove")
  public String taskPostDelete(@PathVariable(value = "id") int id, Model model) {
    Task task = taskRepository.findById(id).orElseThrow();
    taskRepository.delete(task);
    return "redirect:/tasks";
  }

  @GetMapping("/tasks/search")
  public String searchTask(@RequestParam(value = "query") String text, Model model) {

    List<Task> task = taskRepository.searchByTitle(text);
    model.addAttribute("tasks", task);
    if (task.isEmpty()){
      model.addAttribute("empty", "Ничего не найдено");
    }
    return "search";
  }
}
