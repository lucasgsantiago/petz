package com.petz.apiclientes.domain.clientes;

import java.util.List;
import java.util.Optional;

public interface IPetRepository {
    Optional<Pet> findPetById(String id);
    List<Pet> findPetsByDonoId(String donoId);
}
