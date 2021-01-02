package com.petz.apiclientes.domain.clientes;

import com.petz.apiclientes.application.queries.cliente.responses.ClienteResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Optional<Cliente> findFirstByEmail(String email);
    List<ClienteResponse> findAllClientes();
    Cliente findClienteById(@Param("id") String id);
}
