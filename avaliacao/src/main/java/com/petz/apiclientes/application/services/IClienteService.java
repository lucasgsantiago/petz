package com.petz.apiclientes.application.services;


import com.petz.apiclientes.application.commands.cliente.*;
import com.petz.apiclientes.application.queries.cliente.projections.ClienteComPetsProjection;
import com.petz.apiclientes.application.queries.cliente.responses.ClienteResponse;
import com.petz.apiclientes.application.queries.cliente.responses.PageResponse;
import com.petz.apiclientes.application.queries.cliente.responses.PetComDonoResponse;
import com.petz.apiclientes.application.queries.cliente.responses.PetResponse;
import com.petz.apiclientes.wrappers.BusinessException;
import com.petz.apiclientes.wrappers.ClienteAlreadyRegisteredException;
import com.petz.apiclientes.wrappers.ClienteNotFoundException;
import com.petz.apiclientes.wrappers.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    void adicionarCliente(CriarClienteCommand command) throws ClienteAlreadyRegisteredException;
    void alterarCliente(AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException;
    void deletarCliente(String id) throws ResourceNotFoundException, ClienteNotFoundException;
    List<ClienteResponse> obterTodos();
    PageResponse<ClienteResponse> obterClientes(Pageable pageable);
    ClienteComPetsProjection obterPorId(String id) throws ClienteNotFoundException;
    void adicionarPet(AdicionarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException;
    void deletarPet(DeletarPetCommand command) throws ClienteNotFoundException, ResourceNotFoundException;
    void alterarPet(AlterarPetCommand command) throws ClienteNotFoundException, ResourceNotFoundException;
    List<PetResponse> obterPets(String clienteId) throws ResourceNotFoundException, ClienteNotFoundException;
    Optional<PetComDonoResponse> obterPetPorId(String id);



}
