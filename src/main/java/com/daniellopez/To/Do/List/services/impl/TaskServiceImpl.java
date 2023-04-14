package com.daniellopez.To.Do.List.services.impl;
import com.daniellopez.To.Do.List.dtos.TaskDTO;
import com.daniellopez.To.Do.List.models.Client;
import com.daniellopez.To.Do.List.models.Task;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.repositories.TaskRepository;
import com.daniellopez.To.Do.List.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<TaskDTO> getTasks(){
        return taskRepository.findAll().stream().map(task -> new TaskDTO(task)).collect(toList());
    }
    @Override
    public TaskDTO getTask(@PathVariable Long id){
        return taskRepository.findById(id).map(task -> new TaskDTO(task)).orElse(null);
    }
    @Override
    public List<TaskDTO> getCurrentTask (Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getTasks().stream().map(task -> new TaskDTO(task)).collect(toList());
    }
    @Override
    public ResponseEntity<Object> newTask (Authentication authentication, @RequestBody Task newTask){
        Client client = clientRepository.findByEmail(authentication.getName());
        newTask.setClient(client);
        taskRepository.save(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body("La Tarea fue creada Exitosamente");
    }
    @Override
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
    @Override
    public ResponseEntity<Object> deleteTask (@PathVariable Long id){
        Optional <Task> optionalDeletedTask = taskRepository.findById(id);
        if(optionalDeletedTask.isPresent()){
            taskRepository.delete(optionalDeletedTask.get());
            return ResponseEntity.status(HttpStatus.OK).body("La tarea fue borrada");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La tarea no fue encontrada");
        }
    }
}
