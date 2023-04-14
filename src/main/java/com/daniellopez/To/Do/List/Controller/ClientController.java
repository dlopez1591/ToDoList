package com.daniellopez.To.Do.List.Controller;
import com.daniellopez.To.Do.List.dtos.ClientDTO;
import com.daniellopez.To.Do.List.models.Client;
import com.daniellopez.To.Do.List.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RequestMapping("/api")
@RestController
public class ClientController {
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
    public ResponseEntity<Object> edit (@PathVariable Long id, @RequestBody Client client){
        return clientService.edit(id, client);
    }
}