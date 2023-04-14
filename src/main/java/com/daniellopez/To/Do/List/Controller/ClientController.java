package com.daniellopez.To.Do.List.Controller;

import com.daniellopez.To.Do.List.dtos.ClientDTO;
import com.daniellopez.To.Do.List.models.Client;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.repositories.TaskRepository;
import com.daniellopez.To.Do.List.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

//con este requestmapping, indicamos que todas las rutas decritas van a ser /api
@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

    @GetMapping("clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return clientService.getCurrentClient(authentication);
    }

    @GetMapping("/clients/{id}")
    public Optional<ClientDTO> getClient (@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @PostMapping("clients/register")
    public ResponseEntity<Object> register (@RequestBody Client client){
        return clientService.register(client);
    }

    @DeleteMapping("clients/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return clientService.delete(id);
    }
    @PutMapping("clients/{id}")
    public ResponseEntity<Object> edit (@PathVariable Long id, @RequestBody Client updatedClient ){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if(optionalClient.isPresent()){
            Client client = optionalClient.get();
            client.setFirstName(updatedClient.getFirstName());
            client.setLastName(updatedClient.getLastName());
            client.setEmail(updatedClient.getEmail());
            client.setPassword(updatedClient.getPassword());
            clientRepository.save(client);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("El Cliente fue editado");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El Cliente no fue encontrado");
        }
    }

}