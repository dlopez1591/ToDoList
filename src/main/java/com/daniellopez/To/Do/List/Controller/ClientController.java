package com.daniellopez.To.Do.List.Controller;

import com.daniellopez.To.Do.List.dtos.ClientDTO;
import com.daniellopez.To.Do.List.models.Client;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.repositories.TaskRepository;
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
    //ahora vamos a definir un metodo que retorne el listado de clientes
    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        //clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    //Metodo para tener informacion del cliente autenticado
    @RequestMapping("clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
    //Metodo para obtener un cliente por su ID
    @RequestMapping("/clients/{id}")
    public Optional<ClientDTO> getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(client -> new ClientDTO(client));
    }
    //Metodo para crear un cliente
    @PostMapping("clients/register")
    //metodo de tipo publico que va a regresar una response entity recibe como parametro un objeto el metodo se llama register
    public ResponseEntity<Object> register (@RequestBody Client client){
        Client newClient = new Client(client.getFirstName(), client.getLastName(), passwordEncoder.encode(client.getPassword()), client.getEmail());
        clientRepository.save(newClient);
        return ResponseEntity.status(HttpStatus.CREATED).body("El Cliente fue creado exitosamente");
    }

}