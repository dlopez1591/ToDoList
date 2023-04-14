package com.daniellopez.To.Do.List.Controller;
import com.daniellopez.To.Do.List.dtos.TaskDTO;
import com.daniellopez.To.Do.List.models.Task;
import com.daniellopez.To.Do.List.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<TaskDTO> getTasks(){
        return taskService.getTasks();
    }
    @GetMapping("/tasks/{id}")
    public TaskDTO getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }
    @GetMapping("/clients/current/tasks")
    public List<TaskDTO> getCurrentTask (Authentication authentication){
        return taskService.getCurrentTask(authentication);
    }
    @PostMapping("/clients/current/tasks")
    public ResponseEntity<Object> newTask (Authentication authentication, @RequestBody Task newTask){
        return taskService.newTask(authentication, newTask);
    }
    @PutMapping("/task/{id}")
    public ResponseEntity<Object> editTask (@PathVariable Long id, @RequestBody Task updatedTask){
        return taskService.editTask(id, updatedTask);
    }
    @DeleteMapping("/task/{id}")
    public ResponseEntity<Object> deleteTask (@PathVariable Long id){
        return taskService.deleteTask(id);
    }

}
