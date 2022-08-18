package com.infoshareacademy.todolist.service;

import com.infoshareacademy.todolist.model.Task;
import com.infoshareacademy.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public void addTask(Task task) {
        repository.add(task);
        repository.save();
    }

    public List<Task> getTasks() {
        return repository.findAll();
    }

    public void removeTask(Task task) {
        repository.remove(task);
        repository.save();
    }

    public Task findById(Integer id) {
        return repository.find(id);
    }

    public void markAsCompleted(Task task) {
        task.markAsCompleted();
        repository.save();
    }
}
