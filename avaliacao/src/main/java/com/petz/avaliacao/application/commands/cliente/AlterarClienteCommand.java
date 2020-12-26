package com.petz.avaliacao.application.commands.cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AlterarClienteCommand {
    public String id;
    @NotNull
    public String nome;
    @NotNull
    @Email
    public String email;
}
