package com.petz.avaliacao.presentation.controllers;

import com.petz.avaliacao.application.commands.cliente.AdicionarPetCommand;
import com.petz.avaliacao.application.commands.cliente.AlterarClienteCommand;
import com.petz.avaliacao.application.queries.cliente.responses.PetResponse;
import com.petz.avaliacao.application.services.IClienteService;
import com.petz.avaliacao.wrapers.BusinessException;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import com.petz.avaliacao.wrapers.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/v1/pets")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PetController {

    private final IClienteService service;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PetResponse>> get(@PathVariable String clienteId) throws ResourceNotFoundException, ClienteNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPets(clienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPetPorId(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum Pet encontrado!")));
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody AdicionarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException {
        service.adicionarPet(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(command.id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException {
        command.id = id;
        service.alterarCliente(command);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException {
        service.deletarCliente(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
