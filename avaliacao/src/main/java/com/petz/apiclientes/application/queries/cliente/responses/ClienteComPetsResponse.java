package com.petz.apiclientes.application.queries.cliente.responses;

import lombok.Value;

import java.util.Date;
import java.util.Set;

@Value
public class ClienteComPetsResponse {
    private final String id,nome,email;
    private final Date dataCriacao;
    private final Set<PetResponse> pets;

    @Value
    public class Pet {
        private final String id,nome;
        private final int idade;
    }
}
