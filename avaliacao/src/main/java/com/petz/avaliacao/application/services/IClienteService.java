package com.petz.avaliacao.application.services;


import com.petz.avaliacao.application.commands.cliente.AdicionarPetCommand;
import com.petz.avaliacao.application.commands.cliente.AlterarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.DeletarPetCommand;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetResponse;
import com.petz.avaliacao.wrapers.BusinessException;
import com.petz.avaliacao.wrapers.ClienteAlreadyRegisteredException;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import com.petz.avaliacao.wrapers.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    void adicionarCliente(CriarClienteCommand command) throws BusinessException, ClienteAlreadyRegisteredException;
    void alterarCliente(AlterarClienteCommand command) throws BusinessException, ResourceNotFoundException, ClienteNotFoundException;
    void deletarCliente(String id) throws ResourceNotFoundException, ClienteNotFoundException;
    void adicionarPet(AdicionarPetCommand command) throws ResourceNotFoundException, ClienteNotFoundException;
    ClienteResponse obterPorId(String id) throws ClienteNotFoundException;
    List<ClienteResponse> obterTodos();
    List<PetResponse> obterPets(String clienteId) throws ResourceNotFoundException, ClienteNotFoundException;
    Optional<PetResponse> obterPetPorId(String id);

    void deletarPet(DeletarPetCommand command) throws ClienteNotFoundException, ResourceNotFoundException;
}
