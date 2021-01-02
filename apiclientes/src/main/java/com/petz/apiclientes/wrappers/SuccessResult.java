package com.petz.apiclientes.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResult {
    private String correlationId;
    private String message = "Registro salvo com sucesso!";
    private URI link;

    public SuccessResult(String correlationId, URI uri) {
        this.correlationId = correlationId;
        this.link = uri;
    }
}
