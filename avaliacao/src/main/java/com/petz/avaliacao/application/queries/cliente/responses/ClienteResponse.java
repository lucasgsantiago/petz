package com.petz.avaliacao.application.queries.cliente.responses;

import lombok.Value;

import java.util.Date;

@Value
public class ClienteResponse {
    private final String id,nome,email;
    private final Date dataCriacao;
}
