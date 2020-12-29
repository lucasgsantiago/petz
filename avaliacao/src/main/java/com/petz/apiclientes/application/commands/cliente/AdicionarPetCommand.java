package com.petz.apiclientes.application.commands.cliente;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@NoArgsConstructor
public class AdicionarPetCommand {
    public String id = UUID.randomUUID().toString();
    public String donoId;
    @NotNull
    public String nome;
    @NotNull
    public Integer idade;

    public AdicionarPetCommand(String donoId, @NotNull String nome, @NotNull Integer idade) {
        this.donoId = donoId;
        this.nome = nome;
        this.idade = idade;
    }
}
