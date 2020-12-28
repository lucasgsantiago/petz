package com.petz.avaliacao.application.commands.cliente;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

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
