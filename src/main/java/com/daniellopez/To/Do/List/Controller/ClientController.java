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

    //metodo de tipo publico que va a regresar una response entity recibe como parametro un objeto el metodo se llama register
    @PostMapping("clients/register")
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
    //Metodo para eliminar clientes
    @DeleteMapping("clients/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        /*Optional es una clase introducida en Java 8 para tratar con valores que pueden o no estar presentes. Básicamente, Optional es una abstracción
        que representa la posibilidad de que un valor sea null.
        En lugar de devolver un valor null que puede dar lugar a errores de NullPointerException, podemos utilizar Optional para envolver el valor
        y verificar si está presente antes de realizar cualquier operación con él.
        De esta manera, podemos hacer que nuestro código sea más seguro y menos propenso a errores.
        Un objeto Optional puede contener un valor presente o estar vacío. Si un objeto Optional contiene un valor, el método isPresent() devuelve true, mientras
        que si está vacío devuelve false. Para obtener el valor almacenado en un objeto Optional, se puede utilizar el método get(),
        siempre y cuando se haya comprobado previamente que el objeto no está vacío.
        En el ejemplo que me mostraste anteriormente, el método findById() del repositorio devuelve un objeto Optional que puede contener el cliente
        buscado o estar vacío si no se encontró ningún cliente con el ID especificado. Por lo tanto, el método map() se
        utiliza para convertir el objeto Client en un objeto ClientDTO si el objeto Optional contiene un valor.*/

        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            clientRepository.delete(optionalClient.get());
            return ResponseEntity.ok("El cliente fue eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
        }

    }
    //Metodo para editar un cliente
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