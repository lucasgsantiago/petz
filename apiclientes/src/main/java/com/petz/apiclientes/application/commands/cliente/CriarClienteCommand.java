package com.petz.apiclientes.application.commands.cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;
@NoArgsConstructor
public class CriarClienteCommand {
    @JsonIgnore
    public String id = UUID.randomUUID().toString();
    @NotNull
    @Size(max = 60)
    public String nome;
    @NotNull
    @Email
    @Size(min = 10, max = 254)
    public String email;

    public CriarClienteCommand(@NotNull String nome, @NotNull @Email String email) {
        this.nome = nome;
        this.email = email;
    }
}
