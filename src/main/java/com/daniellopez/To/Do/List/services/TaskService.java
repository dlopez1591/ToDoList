package com.daniellopez.To.Do.List.services;

import com.daniellopez.To.Do.List.dtos.TaskDTO;
import com.daniellopez.To.Do.List.models.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasks();
    TaskDTO getTask(Long id);
    List<TaskDTO> getCurrentTask (Authentication authentication);
    ResponseEntity<Object> newTask (Authentication authentication, Task newTask);
    ResponseEntity<Object> editTask (Long id,Task updatedTask);
    ResponseEntity<Object> deleteTask (Long id);
}
