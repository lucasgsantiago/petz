package com.petz.avaliacao.presentation.controllers;

import com.petz.avaliacao.application.commands.cliente.AdicionarPetCommand;
import com.petz.avaliacao.application.commands.cliente.AlterarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.DeletarPetCommand;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetResponse;
import com.petz.avaliacao.application.services.IClienteService;
import com.petz.avaliacao.wrapers.BusinessException;
import com.petz.avaliacao.wrapers.ClienteAlreadyRegisteredException;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import com.petz.avaliacao.wrapers.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private final IClienteService service;

    public ClienteController(IClienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> get(){
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodos());
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponse> obterTodos(){
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponse get(@PathVariable String id) throws ClienteNotFoundException {
        return service.obterPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String criarCliente(@Valid @RequestBody CriarClienteCommand command) throws ClienteAlreadyRegisteredException, BusinessException {
        service.adicionarCliente(command);
        return command.id;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterarCliente(@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException {
        command.id = id;
        service.alterarCliente(command);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws ClienteNotFoundException, ResourceNotFoundException {
        service.deletarCliente(id);
    }

    @PostMapping("/{id}/pets")
    public ResponseEntity<?> post(@PathVariable String id,@Valid @RequestBody AdicionarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException {
        command.donoId = id;
        service.adicionarPet(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(command.id);
    }

    @GetMapping("/{id}/pets")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PetResponse>> obterPets(@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPets(id));
    }

    @GetMapping("/{clienteId}/pets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PetResponse> obterPetPorId(@PathVariable String clienteId,@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPetPorId(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum Pet encontrado!")));
    }

    @PutMapping("/{clienteId}/pets/{id}")
    public ResponseEntity<?> atualizarPet(@PathVariable String clienteId,@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException {
        command.id = id;
        service.alterarCliente(command);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{clienteId}/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPet(@PathVariable String clienteId,@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException {
        service.deletarPet(new DeletarPetCommand(clienteId,id));
    }

}
