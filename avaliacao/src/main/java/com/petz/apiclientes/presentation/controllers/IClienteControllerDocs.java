package com.petz.apiclientes.presentation.controllers;

import com.petz.apiclientes.application.commands.cliente.AdicionarPetCommand;
import com.petz.apiclientes.application.commands.cliente.AlterarClienteCommand;
import com.petz.apiclientes.application.commands.cliente.AlterarPetCommand;
import com.petz.apiclientes.application.commands.cliente.CriarClienteCommand;
import com.petz.apiclientes.application.queries.cliente.responses.ClienteComPetsProjection;
import com.petz.apiclientes.application.queries.cliente.responses.ClienteResponse;
import com.petz.apiclientes.application.queries.cliente.responses.PageResponse;
import com.petz.apiclientes.application.queries.cliente.responses.PetComDonoResponse;
import com.petz.apiclientes.application.queries.cliente.responses.PetResponse;
import com.petz.apiclientes.wrappers.BusinessException;
import com.petz.apiclientes.wrappers.ClienteAlreadyRegisteredException;
import com.petz.apiclientes.wrappers.ClienteNotFoundException;
import com.petz.apiclientes.wrappers.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Gerenciamento de Clientes e Pets")
public interface IClienteControllerDocs {

    @ApiOperation(value = "Cria um Cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente criado com sucesso."),
            @ApiResponse(code = 400, message = "Cliente com o email informado já está registrado no sistema.")
    })
    ResponseEntity<?> criarCliente(@Valid @RequestBody CriarClienteCommand command) throws ClienteAlreadyRegisteredException;

    @ApiOperation(value = "Atualiza um Cliente dado um Id válido")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente alterado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado."),
            @ApiResponse(code = 400, message = "Existe um outro Cliente cadastrado com este e-mail!"),
            @ApiResponse(code = 400, message = "Cliente já está registrado no sistema.")
    })
    ResponseEntity<?> alterarCliente(@PathVariable String id, @Valid @RequestBody AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException;

    @ApiOperation(value = "Retorna um Cliente a partir de um Id válido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
    ClienteComPetsProjection obterCliente(@PathVariable String id) throws ClienteNotFoundException;

    @ApiOperation(value = "Retorna uma lista paginada de Clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de Clientes registrados no sistema"),
    })
    PageResponse<ClienteResponse> obterClientes(@PageableDefault(value = 20, sort = "nome",size = 20,page = 0, direction = Sort.Direction.ASC) Pageable pageable);

    @ApiOperation(value = "Deleta Cliente encontrado a partir de um Id válido")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Cliente deletado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
    void deletarCliente(@PathVariable String id) throws ClienteNotFoundException, ResourceNotFoundException;

    @ApiOperation(value = "Retorna uma lista de Pets a partir de um Id de um Cliente válido.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de Pets registrados no sistema"),
    })
    List<PetResponse> obterPets(@PathVariable String id) throws ResourceNotFoundException, ClienteNotFoundException;

    @ApiOperation(value = "Retorna um Pet encontrado a partir de um Id de um Cliente válido e um Id de Pet válido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pet deletado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado."),
            @ApiResponse(code = 404, message = "Pet não encontrado.")
    })
    ResponseEntity<PetComDonoResponse> obterPetPorId(@PathVariable String id, @PathVariable String petId) throws ResourceNotFoundException;

    @ApiOperation(value = "Adiciona um Pet a um Cliente válido.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pet adicionado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado."),
    })
    ResponseEntity<?> adicionarPet(@PathVariable String id,@Valid @RequestBody AdicionarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException;

    @ApiOperation(value = "Atualiza um Pet dado um Id de um Cliente válido e um Id de Pet válido.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pet alterado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado."),
            @ApiResponse(code = 404, message = "Pet não encontrado.")
    })
    ResponseEntity<?> atualizarPet(@PathVariable String id, @PathVariable String petId,@Valid @RequestBody AlterarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException;

    @ApiOperation(value = "Deleta Pet encontrado a partir de um Id de um Cliente válido e um Id de Pet válido")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Pet deletado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado."),
            @ApiResponse(code = 404, message = "Pet não encontrado.")
    })
    void deletarPet(@PathVariable String id,@PathVariable String petId) throws ResourceNotFoundException, ClienteNotFoundException;
}
