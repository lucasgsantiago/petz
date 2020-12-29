package com.petz.apiclientes.infrastructure.repositories;

import com.petz.apiclientes.domain.clientes.IPetRepository;
import com.petz.apiclientes.domain.clientes.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, String>, IPetRepository {
}
