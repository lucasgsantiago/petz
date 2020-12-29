package com.petz.apiclientes.application.commands.cliente;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@NoArgsConstructor
@AllArgsConstructor
public class AlterarClienteCommand {
    public String id;
    @NotNull
    public String nome;
    @NotNull
    @Email
    public String email;


}
