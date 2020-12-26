package com.petz.avaliacao.infrastructure.repositories;

import com.petz.avaliacao.domain.Clientes.Cliente;
import com.petz.avaliacao.domain.Clientes.IClienteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>, IClienteRepository {
}
