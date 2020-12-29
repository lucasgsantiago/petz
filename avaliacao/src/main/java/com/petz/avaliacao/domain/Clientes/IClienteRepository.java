package com.petz.avaliacao.domain.clientes;

import com.petz.avaliacao.application.queries.cliente.projections.ClienteComPetsProjection;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Optional<Cliente> findFirstByEmail(String email);
    List<ClienteResponse> findAllClientes();
    ClienteComPetsProjection findClienteById(String id);
}
