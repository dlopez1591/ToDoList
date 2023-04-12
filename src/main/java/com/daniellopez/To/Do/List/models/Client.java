package com.daniellopez.To.Do.List.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    //propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    //declaracion de la relacion de client con task
    @OneToMany(mappedBy="client", fetch = FetchType.EAGER)
    Set<Task> tasks = new HashSet<>();

    //constructores
    public Client() {}

    public Client(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    //metodos


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //Setter de Task
    public Set<Task> getTasks(){
        return tasks;
    }

    //Creo un metodo que me permite agregar una tarea a un cliente

    public void addTask(Task task) {
        task.setClient(this);
        tasks.add(task);
    }


    public  String toString(){
        return firstName+" "+lastName+" "+email;
    }

}