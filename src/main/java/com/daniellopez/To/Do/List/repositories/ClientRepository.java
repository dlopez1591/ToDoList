package com.daniellopez.To.Do.List.repositories;

import com.daniellopez.To.Do.List.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//con esta anotacion lo que hago es indicar que un repositorio es rest
@RepositoryRestResource
public interface ClientRepository extends JpaRepository <Client, Long> {
}
