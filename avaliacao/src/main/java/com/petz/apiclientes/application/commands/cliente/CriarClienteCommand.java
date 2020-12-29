package com.petz.apiclientes.application.commands.cliente;

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
