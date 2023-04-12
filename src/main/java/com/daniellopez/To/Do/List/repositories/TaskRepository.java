package com.daniellopez.To.Do.List.repositories;
import com.daniellopez.To.Do.List.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository <Task,Long> {

}
