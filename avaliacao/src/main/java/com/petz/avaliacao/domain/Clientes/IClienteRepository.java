package com.petz.avaliacao.domain.clientes;

import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Optional<Cliente> findFirstByEmail(String email);
    List<ClienteResponse> findAllClientes();
}
