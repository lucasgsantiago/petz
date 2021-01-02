package com.petz.apiclientes.application.queries.cliente.responses;

import lombok.Value;

@Value
public class PetResponse {
    private final String id,nome;
    private final int idade;
}
