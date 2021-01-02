package com.petz.apiclientes.infrastructure.repositories;

import com.petz.apiclientes.application.queries.cliente.responses.ClienteResponse;
import com.petz.apiclientes.domain.clientes.Cliente;
import com.petz.apiclientes.domain.clientes.IClienteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>, IClienteRepository {
    @Query(value = "SELECT new com.petz.apiclientes.application.queries.cliente.responses.ClienteResponse(c.id, c.nome, c.email, c.dataCriacao) FROM Cliente c")
    List<ClienteResponse> findAllClientes();

}

