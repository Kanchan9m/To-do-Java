package com.example.todos.todoResponseDto;

import java.time.LocalDate;

public class TodoResponse {

    private String task_name;
    private String description;
    private LocalDate date;
    private Boolean completed;

    public TodoResponse(String task_name, String description, LocalDate date, Boolean completed){
        this.task_name = task_name;
        this.description = description;
        this.date = date;
        this.completed = completed;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
