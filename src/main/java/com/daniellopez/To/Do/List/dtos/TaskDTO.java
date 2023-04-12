package com.daniellopez.To.Do.List.dtos;

import com.daniellopez.To.Do.List.models.Task;
import com.daniellopez.To.Do.List.models.taskStatus;

import java.time.LocalDate;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private taskStatus status;

    public TaskDTO (Task task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.status = task.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public taskStatus getStatus() {
        return status;
    }
}
