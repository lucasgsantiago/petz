package com.petz.avaliacao.presentation.controllers;

import com.petz.avaliacao.application.commands.cliente.AdicionarPetCommand;
import com.petz.avaliacao.application.commands.cliente.AlterarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.DeletarPetCommand;
import com.petz.avaliacao.application.queries.cliente.projections.ClienteComPetsProjection;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PageResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetComDonoResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetResponse;
import com.petz.avaliacao.application.services.IClienteService;
import com.petz.avaliacao.wrapers.BusinessException;
import com.petz.avaliacao.wrapers.ClienteAlreadyRegisteredException;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import com.petz.avaliacao.wrapers.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/all")
    @Cacheable("clientes")
    public ResponseEntity<List<ClienteResponse>> get(){
        return ResponseEntity.status(HttpStatus.OK).body(service.obterTodos());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable("clientes")
    public PageResponse<ClienteResponse> obterClientes(@PageableDefault(value = 20, sort = "nome",size = 20,page = 0, direction = Sort.Direction.ASC) Pageable pageable){
        return service.obterClientes(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteComPetsProjection obterCliente(@PathVariable String id) throws ClienteNotFoundException {
        return service.obterPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "clientes", allEntries = true)
    public String criarCliente(@Valid @RequestBody CriarClienteCommand command) throws ClienteAlreadyRegisteredException, BusinessException {
        service.adicionarCliente(command);
        return command.id;
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "clientes", allEntries = true)
    public ResponseEntity<?> alterarCliente(@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException {
        command.id = id;
        service.alterarCliente(command);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "clientes", allEntries = true)
    public void delete(@PathVariable String id) throws ClienteNotFoundException, ResourceNotFoundException {
        service.deletarCliente(id);
    }

    @PostMapping("/{id}/pets")
    @CacheEvict(value = "clientes", allEntries = true)
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
    public ResponseEntity<PetComDonoResponse> obterPetPorId(@PathVariable String clienteId, @PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPetPorId(id).orElseThrow(() -> new ResourceNotFoundException("Nenhum Pet encontrado!")));
    }

    @PutMapping("/{clienteId}/pets/{id}")
    @CacheEvict(value = "clientes", allEntries = true)
    public ResponseEntity<?> atualizarPet(@PathVariable String clienteId,@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException {
        command.id = id;
        service.alterarCliente(command);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{clienteId}/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "clientes", allEntries = true)
    public void deletarPet(@PathVariable String clienteId,@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException {
        service.deletarPet(new DeletarPetCommand(clienteId,id));
    }

}
