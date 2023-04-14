package com.daniellopez.To.Do.List.services.impl;

import com.daniellopez.To.Do.List.dtos.ClientDTO;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImpl implements ClientService {
@Autowired
    private ClientRepository clientRepository;
@Override
public List<ClientDTO> getClients() {
    return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
}
@Override
public ClientDTO getCurrentClient(Authentication authentication) {
    return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
}

}
