package com.daniellopez.To.Do.List.services;

import com.daniellopez.To.Do.List.dtos.ClientDTO;
import com.daniellopez.To.Do.List.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientDTO> getClients();
    ClientDTO getCurrentClient (Authentication authentication);
    Optional<ClientDTO> getClient(Long id);
    ResponseEntity<Object> register (Client client);
    ResponseEntity<Object> delete (Long id);
    
}

