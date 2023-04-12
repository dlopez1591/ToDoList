package com.daniellopez.To.Do.List.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Task {
    //Propiedades
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private taskStatus status;

    //declaracion de la relacion en las bases de datos
    //La anotacion Many to one, indica que muchas task son asignadas a un solo cliente
    // FetchType.EAGER indica a JPA que cuando una task sea cargada de la base de datos,
    //tambien debe ser cargado el cliente
    //Join Column dice que columna debe tener el id del cliente

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    //Constructores
    /* Para que Spring pueda crear e inyectar estos objetos en tu aplicación, es necesario que haya
    un constructor vacío en cada clase.*/

    public Task (){}

    public Task(String title, String description, LocalDate dueDate, taskStatus status, Client client) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.client = client;
    }

    //Getter y Setter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public taskStatus getStatus() {
        return status;
    }

    public void setStatus(taskStatus status) {
        this.status = status;
    }

    //Getter y Setter de CLient
    //Utilizo la anotacion JsonIgnore, por que tengo recurrencia, es decir Un Cliente tiene una tarea, una tarea tiene un cliente, que a su ves tiene una tarea que a su ves tiene un cliente...
    //Eso se llama recurrecnia, en este caso le pido al JSON "no mostrar" el cilente y de esa manera detengo el loop de recurrencia
    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    @Override
    public  String toString(){
        return title+" "+description+" "+dueDate+" "+status;
    }
}
