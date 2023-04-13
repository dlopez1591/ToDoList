package com.daniellopez.To.Do.List.Controller;

import com.daniellopez.To.Do.List.dtos.ClientDTO;
import com.daniellopez.To.Do.List.repositories.ClientRepository;
import com.daniellopez.To.Do.List.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
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
    @RequestMapping("clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        //clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    //Metodo para tener informacion del cliente autenticado

}