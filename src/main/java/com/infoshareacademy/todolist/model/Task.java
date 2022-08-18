package com.infoshareacademy.todolist.model;

import com.infoshareacademy.todolist.enums.Category;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class Task {
    private Integer id;

    @NotBlank
    private String description;

    @NotNull
    private Category category;

    @Min(value = 1)
    @Max(value = 5)
    private Integer priority;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future()
    @NotNull()
    private LocalDate deadline;

    private Boolean completed = false;

    public long daysToDeadline() {
        long diff = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        if (diff < 0) return 0;
        return diff;
    }

    public void markAsCompleted() {
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
