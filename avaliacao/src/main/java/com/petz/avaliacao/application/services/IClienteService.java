package com.petz.avaliacao.application.services;


import com.petz.avaliacao.application.commands.cliente.*;
import com.petz.avaliacao.application.queries.cliente.projections.ClienteComPetsProjection;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PageResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetComDonoResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetResponse;
import com.petz.avaliacao.domain.clientes.Cliente;
import com.petz.avaliacao.wrapers.BusinessException;
import com.petz.avaliacao.wrapers.ClienteAlreadyRegisteredException;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import com.petz.avaliacao.wrapers.ResourceNotFoundException;
import org.springframework.data.domain.Page;
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
