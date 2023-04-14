package com.daniellopez.To.Do.List.services.impl;

import com.daniellopez.To.Do.List.dtos.ClientDTO;
import com.daniellopez.To.Do.List.models.Client;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImpl implements ClientService {
@Autowired
    private ClientRepository clientRepository;
@Autowired
    private PasswordEncoder passwordEncoder;
@Override
public List<ClientDTO> getClients() {
    return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
}
@Override
public ClientDTO getCurrentClient(Authentication authentication) {
    return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
}
@Override
public Optional<ClientDTO> getClient(@PathVariable Long id){
    return clientRepository.findById(id).map(client -> new ClientDTO(client));
}
@Override
public ResponseEntity<Object> register (@RequestBody Client client){
    if (client.getFirstName().isEmpty()) {
        return new ResponseEntity<>("Falta El Nombre", HttpStatus.BAD_REQUEST);
    } else if (client.getLastName().isEmpty()) {
        return new ResponseEntity<>("Falta el Apellido", HttpStatus.BAD_REQUEST);
    } else if (client.getEmail().isEmpty()) {
        return new ResponseEntity<>("Falta el Email", HttpStatus.BAD_REQUEST);
    } else if (client.getPassword().isEmpty()) {
        return new ResponseEntity<>("Falta El Password", HttpStatus.BAD_REQUEST);
    } else if (clientRepository.findByEmail(client.getEmail()) != null) {
        return new ResponseEntity<>("El Email ya se encuentra en uso", HttpStatus.FORBIDDEN);
    } else {
        Client newClient = new Client(client.getFirstName(), client.getLastName(),
                passwordEncoder.encode(client.getPassword()), client.getEmail());
        clientRepository.save(newClient);
        return ResponseEntity.status(HttpStatus.CREATED).body("El Cliente fue creado exitosamente");
    }
}
@Override
public ResponseEntity<Object> delete(@PathVariable Long id) {

    Optional<Client> optionalClient = clientRepository.findById(id);
    if (optionalClient.isPresent()) {
        clientRepository.delete(optionalClient.get());
        return ResponseEntity.ok("El cliente fue eliminado exitosamente");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
    }

}
}
