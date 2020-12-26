package com.petz.avaliacao.application.services;

import com.petz.avaliacao.application.commands.cliente.AdicionarPetCommand;
import com.petz.avaliacao.application.commands.cliente.AlterarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.DeletarPetCommand;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetResponse;
import com.petz.avaliacao.domain.Clientes.Cliente;
import com.petz.avaliacao.infrastructure.repositories.ClienteRepository;
import com.petz.avaliacao.infrastructure.repositories.PetRepository;
import com.petz.avaliacao.wrapers.*;
import lombok.AllArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteService implements IClienteService {

    private final ClienteRepository respository;
    private final PetRepository petRespository;
    private final ClienteMapper mapper = ClienteMapper.INSTANCE;

    @Override
    public void adicionarCliente(CriarClienteCommand command) throws ClienteAlreadyRegisteredException {
        verificarSeClienteJaEstaCadastrado(command.email);
        var cliente = new Cliente(command.id, command.nome, command.email);
        respository.save(cliente);
    }

    @Override
    public void alterarCliente(AlterarClienteCommand command) throws BusinessException, ClienteNotFoundException {
        var cliente = verificarSeClienteExiste(command.id);
        var res = respository.findFirstByEmail(command.email);
        if(res.isPresent() && !res.get().getId().equalsIgnoreCase(cliente.getId())){
            throw new BusinessException("Existe um outro Cliente cadastrado com este e-mail!");
        }
        cliente.alterar(command.nome, command.email);
        respository.save(cliente);
    }

    @Override
    public void deletarCliente(String id) throws ClienteNotFoundException {
        var cliente = verificarSeClienteExiste(id);
        respository.delete(cliente);
    }

    @Override
    public void adicionarPet(AdicionarPetCommand command) throws ClienteNotFoundException {
        var cliente = verificarSeClienteExiste(command.donoId);
        cliente.adicionarPet(mapper.converter(command));
        respository.saveAndFlush(cliente);
    }

    @Override
    public ClienteResponse obterPorId(String id) throws ClienteNotFoundException {
        if(id == null ) throw new IllegalArgumentException("Identificador inválido:" + id);
        var cliente = verificarSeClienteExiste(id);
        return mapper.converter(cliente);
    }

    @Override
    public List<ClienteResponse> obterTodos(){
        return mapper.converter(respository.findAll());
    }

    @Override
    public List<PetResponse> obterPets(String clienteId) throws ClienteNotFoundException {
        verificarSeClienteExiste(clienteId);
        return mapper.converterPets(petRespository.findPetsByDonoId(clienteId));
    }

    @Override
    public Optional<PetResponse> obterPetPorId(String id) {
        var pet = petRespository.findPetById(id);
        if(pet.isPresent()){
            return Optional.ofNullable(mapper.converter(pet.get()));
        }
        return Optional.empty();
    }

    @Override
    public void deletarPet(DeletarPetCommand command) throws ClienteNotFoundException, ResourceNotFoundException {
        var cliente = verificarSeClienteExiste(command.donoId);
        cliente.deletarPet(command.petId);
        respository.save(cliente);
    }

    private void verificarSeClienteJaEstaCadastrado(String email) throws ClienteAlreadyRegisteredException {
        if(StringHelper.isEmpty(email)) throw new IllegalArgumentException("Email inválido:" + email);
        Optional<Cliente> cliente = respository.findFirstByEmail(email);
        if(cliente.isPresent()){
            throw new ClienteAlreadyRegisteredException(email);
        }
    }

    private Cliente verificarSeClienteExiste(String id) throws ClienteNotFoundException {
        if(StringHelper.isEmpty(id)) throw new IllegalArgumentException("Identificador inválido:" + id);
        return respository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
    }
}
