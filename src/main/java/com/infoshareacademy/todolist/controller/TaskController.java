package com.infoshareacademy.todolist.controller;

import com.infoshareacademy.todolist.model.Task;
import com.infoshareacademy.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String showAll(Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        model.addAttribute("count", taskService.getTasks().size());
        return "task-list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute Task task, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "task-form";
        }

        // no errors
        taskService.addTask(task);
        attributes.addFlashAttribute("created", task.getDescription());

        return "redirect:/";
    }

    @GetMapping("/tasks/{id}/remove")
    public String removeTask(@PathVariable Integer id, RedirectAttributes attributes) {
        Task task = taskService.findById(id);
        attributes.addFlashAttribute("deleted", task.getDescription());
        taskService.removeTask(task);

        return "redirect:/";
    }

    @GetMapping("/tasks/{id}/mark-as-completed")
    public String completeTask(@PathVariable Integer id) {
        Task task = taskService.findById(id);
        taskService.markAsCompleted(task);

        return "redirect:/";
    }
}
