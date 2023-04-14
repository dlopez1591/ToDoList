package com.daniellopez.To.Do.List;

import com.daniellopez.To.Do.List.models.Client;
import com.daniellopez.To.Do.List.models.Task;
import com.daniellopez.To.Do.List.models.taskStatus;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class ToDoListApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}
	/* es una anotación que le indica a Spring que cree y administre un objeto en tu aplicación (Esto significa que Spring se encarga de la creación, configuración y destrucción de la instancia del objeto,
	 lo que te permite concentrarte en el código de tu aplicación y no preocuparte por la gestión de los objetos.)
	 */
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, TaskRepository taskRepository) {
		return (args) -> {
			Client daniel = new Client("Daniel", "Lopez", passwordEncoder.encode("daniel"), "dlopez1591@gmail.com");
			Client yenny = new Client ("Yenny", "Hernandez", passwordEncoder.encode("yenny"),  "yenny123@gmail.com");
			Task tareaUno = new Task("Tarea Uno", "Crear una tarea", LocalDate.now(), taskStatus.Comenzada, daniel);
			Task tareaDos = new Task("Tarea Dos", "Crear otra tarea", LocalDate.now(), taskStatus.Desarrollando, yenny);
			Task tareaTres = new Task ("Tarea Tres", "Creando mas tareas", LocalDate.now(),taskStatus.Finalizada, daniel);
			daniel.addTask(tareaUno);
			yenny.addTask(tareaDos);
			daniel.addTask(tareaTres);
			clientRepository.save(daniel);
			clientRepository.save(yenny);
			taskRepository.save(tareaUno);
			taskRepository.save(tareaDos);
			taskRepository.save(tareaTres);
		};
	}
}
