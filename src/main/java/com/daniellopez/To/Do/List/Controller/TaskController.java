package com.daniellopez.To.Do.List.Controller;

import com.daniellopez.To.Do.List.dtos.TaskDTO;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class TaskController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TaskRepository taskRepository;
    //Metodo para Recuperar todas las tareas
    @GetMapping("/tasks")
    public List<TaskDTO> getTasks(){
        return taskRepository.findAll().stream().map(task -> new TaskDTO(task)).collect(Collectors.toList());
    }
}
