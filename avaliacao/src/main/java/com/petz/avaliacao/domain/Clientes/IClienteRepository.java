package com.petz.avaliacao.domain.Clientes;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository {
    Optional<Cliente> findFirstByEmail(String email);
}
