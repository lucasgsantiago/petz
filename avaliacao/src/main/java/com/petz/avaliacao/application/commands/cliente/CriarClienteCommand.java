package com.petz.avaliacao.application.commands.cliente;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;
@NoArgsConstructor
public class CriarClienteCommand {
    public String id = UUID.randomUUID().toString();
    @NotNull
    public String nome;
    @NotNull
    @Email
    public String email;

    public CriarClienteCommand(@NotNull String nome, @NotNull @Email String email) {
        this.nome = nome;
        this.email = email;
    }
}
