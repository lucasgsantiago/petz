package com.petz.avaliacao.infrastructure.repositories;

import com.petz.avaliacao.domain.Clientes.IPetRepository;
import com.petz.avaliacao.domain.Clientes.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, String>, IPetRepository {
}
