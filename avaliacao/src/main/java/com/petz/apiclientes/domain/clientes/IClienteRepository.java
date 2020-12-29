package com.petz.apiclientes.domain.clientes;

import com.petz.apiclientes.application.queries.cliente.responses.ClienteComPetsProjection;
import com.petz.apiclientes.application.queries.cliente.responses.ClienteResponse;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Optional<Cliente> findFirstByEmail(String email);
    List<ClienteResponse> findAllClientes();
    ClienteComPetsProjection findClienteById(String id);
}
