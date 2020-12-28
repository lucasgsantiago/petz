package com.petz.avaliacao.presentation.controllers;

import com.petz.avaliacao.application.commands.cliente.*;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController implements IClienteControllerDocs{
    private static final String PATH = "clientes";
    private final IClienteService service;

    public ClienteController(IClienteService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable("clientes")
    public PageResponse<ClienteResponse> obterClientes(@PageableDefault(value = 20, sort = "nome",size = 20,page = 0, direction = Sort.Direction.ASC) Pageable pageable){
        return service.obterClientes(pageable);
    }

    @GetMapping("/all")
    @Cacheable("clientes")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponse> get(){
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteComPetsProjection obterCliente(@PathVariable String id) throws ClienteNotFoundException {
        return service.obterPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = PATH, allEntries = true)
    public ResponseEntity<?> criarCliente(@Valid @RequestBody CriarClienteCommand command) throws ClienteAlreadyRegisteredException, BusinessException {
        service.adicionarCliente(command);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/"+PATH+"/").path(command.id).build().toUri();
        return ResponseEntity.created(locationUri).body(command.id);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = PATH, allEntries = true)
    public ResponseEntity<?> alterarCliente(@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException {
        command.id = id;
        service.alterarCliente(command);
        var headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
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
    public List<PetResponse> obterPets(@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException {
        return service.obterPets(id);
    }

    @GetMapping("/{id}/pets/{petId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PetComDonoResponse> obterPetPorId(@PathVariable String id, @PathVariable String petId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPetPorId(petId).orElseThrow(() -> new ResourceNotFoundException("Nenhum Pet encontrado!")));
    }

    @PutMapping("/{id}/pets/{petId}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = "clientes", allEntries = true)
    public void atualizarPet(@PathVariable String id, @PathVariable String petId,@Valid @RequestBody AlterarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException {
        command.petId = petId;
        command.donoId = id;
        service.alterarPet(command);
    }

    @DeleteMapping("/{clienteId}/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "clientes", allEntries = true)
    public void deletarPet(@PathVariable String clienteId,@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException {
        service.deletarPet(new DeletarPetCommand(clienteId,id));
    }

}
