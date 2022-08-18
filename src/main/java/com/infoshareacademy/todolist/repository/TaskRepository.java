package com.infoshareacademy.todolist.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.infoshareacademy.todolist.model.Task;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    private final Path path = Path.of("src", "main", "resources", "task.json");
    private final ObjectMapper mapper = new ObjectMapper();
    protected List<Task> tasks;

    protected TaskRepository() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(df);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());

        loadData();
    }

    public Task find(Integer id) {
        return tasks.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Task> findAll() {
        return tasks;
    }

    public void add(Task object) {
        // id of last element + 1
        object.setId(tasks.get(tasks.size() - 1).getId() + 1);
        tasks.add(object);
    }

    public void remove(Task object) {
        tasks.remove(object);
    }

    public void save() {
        try {
            mapper.writeValue(path.toFile(), tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData() {
        tasks = new ArrayList<>();

        if (Files.exists(path)) {
            try {
                tasks = mapper.readValue(path.toFile(), new TypeReference<List<Task>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}