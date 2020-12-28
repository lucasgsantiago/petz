package com.petz.avaliacao.application.commands.cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CriarClienteCommand {
    public String id = UUID.randomUUID().toString();
    @NotNull
    public String nome;
    @NotNull
    @Email
    public String email;

    public CriarClienteCommand() {}

    public CriarClienteCommand(@NotNull String nome, @NotNull @Email String email) {
        this.nome = nome;
        this.email = email;
    }
}