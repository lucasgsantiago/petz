package com.petz.apiclientes.application.queries.cliente.responses;

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
