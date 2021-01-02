package com.petz.apiclientes.application.commands.cliente;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
public class AlterarClienteCommand {
    public String id;

    @NotNull
    @Size(max = 50)
    public String nome;

    @NotNull
    @Email
    @Size(min = 10, max = 254)
    public String email;

}
