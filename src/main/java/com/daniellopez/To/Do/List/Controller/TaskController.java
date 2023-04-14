package com.daniellopez.To.Do.List.Controller;

import com.daniellopez.To.Do.List.dtos.TaskDTO;
import com.daniellopez.To.Do.List.models.Client;
import com.daniellopez.To.Do.List.models.Task;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        return taskRepository.findAll().stream().map(task -> new TaskDTO(task)).collect(toList());

    }
    //Metodo para recuperar una tarea con un ID
    @GetMapping("/tasks/{id}")
    public TaskDTO getTask(@PathVariable Long id){
        return taskRepository.findById(id).map(task -> new TaskDTO(task)).orElse(null);
    }
    //Recuperar tareas del cliente autenticado
    @GetMapping("/clients/current/tasks")
    public List<TaskDTO> getCurrentTask (Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getTasks().stream().map(task -> new TaskDTO(task)).collect(toList());
    }
    //Metodo para crear una tarea al cliente autenticado
    @PostMapping("/clients/current/tasks")
    public ResponseEntity<Object> newTask (Authentication authentication, @RequestBody Task newTask){
        //Se crea el cliente
        Client client = clientRepository.findByEmail(authentication.getName());
        //La tarea ya viene en un body desde el front, se le asigna al cleinte
        newTask.setClient(client);
        //Se Guarda la tarea
        taskRepository.save(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body("La Tarea fue creada Exitosamente");
    }

    //Metodo para editar una tarea
    @PutMapping("/task/{id}")
    public ResponseEntity<Object> editTask (@PathVariable Long id, @RequestBody Task updatedTask){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            //busco la tarea que quiero editar por el ID y la guardo
            Task task = optionalTask.get();
            //Busco el cliente asociado a la tarea
            Client client = task.getClient();
            //Creo una nueva instancia
            Task updated = new Task(updatedTask.getTitle(), updatedTask.getDescription(), updatedTask.getDueDate(), updatedTask.getStatus(), client);
            taskRepository.save(updated);
            return ResponseEntity.status(HttpStatus.OK).body("La tarea ha sido modificada");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro la tarea");
        }
    }
    //Metodo para eliminar una task
    //1. Necesito el tipo de Request y ruta
    @DeleteMapping("/task/{id}")
    //2. crear el metodo, teniendo claro que retorna y por que
    public ResponseEntity<Object> deleteTask (@PathVariable Long id){
        //3. aplicar los metodos del repositorio de acuerdo a lo necesaria
        Optional <Task> optionalDeletedTask = taskRepository.findById(id);
        if(optionalDeletedTask.isPresent()){
            taskRepository.delete(optionalDeletedTask.get());
            //4. indicar la respuesta
            return ResponseEntity.status(HttpStatus.OK).body("La tarea fue borrada");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tarea no fue encontrada");
        }
    }

}
