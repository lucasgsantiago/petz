package com.petz.avaliacao.application.queries.cliente.projections;

import java.util.Set;

public interface ClienteComPetsProjection {
    String getId();
    String getNome();
    String getEmail();
    Set<Pet> getPets();

    interface Pet {
        String getId();
        String getNome();
        int getIdade();
    }
}
