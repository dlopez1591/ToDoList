package com.daniellopez.To.Do.List.dtos;

import com.daniellopez.To.Do.List.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Set<TaskDTO> tasks;

    public ClientDTO (Client client){
        //por esta razon y por encapsulamiento es que fueron creados los Getter y Setters
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.password = client.getPassword();
        this.email = client.getEmail();
        this.tasks = client.getTasks().stream().map(tasks -> new TaskDTO(tasks)).collect(Collectors.toSet());
    }

    //Se Deben crear Getters

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set <TaskDTO> getTasks(){
        return tasks;
    }
}
