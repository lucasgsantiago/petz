package com.petz.apiclientes.presentation.controllers;

import com.petz.apiclientes.application.commands.cliente.*;
import com.petz.apiclientes.application.queries.cliente.responses.*;
import com.petz.apiclientes.application.queries.cliente.responses.PetDetalheResponse;
import com.petz.apiclientes.application.services.IClienteService;
import com.petz.apiclientes.wrappers.*;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/"+ClienteController.RESOURCE)
public class ClienteController implements IClienteControllerDocs{

    static final String RESOURCE = "clientes";
    private final IClienteService service;

    public ClienteController(IClienteService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(RESOURCE)
    public PageResponse<ClienteResponse> obterClientes(@PageableDefault(value = 20, sort = "nome",size = 20,page = 0, direction = Sort.Direction.ASC) Pageable pageable){
        return service.obterClientes(pageable);
    }

    @GetMapping("/all")
    @Cacheable(RESOURCE)
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponse> get(){
        return service.obterTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDetalheResponse obterCliente(@PathVariable String id) throws ClienteNotFoundException {
        return service.obterClientePorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = RESOURCE, allEntries = true)
    public ResponseEntity<?> criarCliente(@Valid @RequestBody CriarClienteCommand command) throws ClienteAlreadyRegisteredException {
        service.adicionarCliente(command);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/").path(command.id).build().toUri();
        return ResponseEntity.created(locationUri).body(new SuccessResult(command.id,locationUri));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = RESOURCE, allEntries = true)
    public ResponseEntity<?> alterarCliente(@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException {
        command.id = id;
        service.alterarCliente(command);
        var headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = RESOURCE, allEntries = true)
    public void deletarCliente(@PathVariable String id) throws ClienteNotFoundException, ResourceNotFoundException {
        service.deletarCliente(id);
    }

    @GetMapping("/{id}/pets")
    @ResponseStatus(HttpStatus.OK)
    public List<PetResponse> obterPets(@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException {
        return service.obterPets(id);
    }

    @GetMapping("/{id}/pets/{petId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PetDetalheResponse> obterPetPorId(@PathVariable String id, @PathVariable String petId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.obterPetPorId(petId).orElseThrow(() -> new ResourceNotFoundException("Nenhum Pet encontrado!")));
    }

    @PostMapping("/{id}/pets")
    @CacheEvict(value = RESOURCE, allEntries = true)
    public ResponseEntity<?> adicionarPet(@PathVariable String id,@Valid @RequestBody AdicionarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException {
        command.donoId = id;
        service.adicionarPet(command);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/").path(command.id).build().toUri();
        return ResponseEntity.created(locationUri).body(new SuccessResult(command.id,locationUri));
    }

    @PutMapping("/{id}/pets/{petId}")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(value = RESOURCE, allEntries = true)
    public ResponseEntity<?> atualizarPet(@PathVariable String id, @PathVariable String petId,@Valid @RequestBody AlterarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException {
        command.petId = petId;
        command.donoId = id;
        service.alterarPet(command);
        var headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = RESOURCE, allEntries = true)
    public void deletarPet(@PathVariable String id,@PathVariable String petId) throws ResourceNotFoundException, ClienteNotFoundException {
        service.deletarPet(new DeletarPetCommand(id,petId));
    }

}
