package com.petz.avaliacao.domain.Clientes;

import java.util.List;
import java.util.Optional;

public interface IPetRepository {
    Optional<Pet> findPetById(String id);
    List<Pet> findPetsByDonoId(String donoId);
}
