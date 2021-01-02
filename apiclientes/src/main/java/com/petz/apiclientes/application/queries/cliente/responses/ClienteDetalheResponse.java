package com.petz.apiclientes.application.queries.cliente.responses;

import lombok.Value;

import java.util.Date;
import java.util.Set;

@Value
public class ClienteDetalheResponse {
    private final String id,nome,email;
    private final Date dataCriacao;
    private final Set<PetResponse> pets;

}
