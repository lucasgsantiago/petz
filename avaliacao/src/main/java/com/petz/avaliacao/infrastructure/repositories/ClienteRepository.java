package com.petz.avaliacao.infrastructure.repositories;

import com.petz.avaliacao.application.queries.cliente.projections.ClienteComPetsProjection;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.domain.clientes.Cliente;
import com.petz.avaliacao.domain.clientes.IClienteRepository;
import com.petz.avaliacao.domain.clientes.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>, IClienteRepository {
    @Query(value = "SELECT new com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse(c.id, c.nome, c.email, c.dataCriacao) FROM Cliente c")
    List<ClienteResponse> findAllClientes();

    ClienteComPetsProjection findClienteById(@Param("id") String id);
    <T> T findClienteById(String id, Class<T> type);

}

