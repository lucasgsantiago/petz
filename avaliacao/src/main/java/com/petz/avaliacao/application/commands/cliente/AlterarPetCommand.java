package com.petz.avaliacao.application.commands.cliente;

import javax.validation.constraints.NotNull;

public class AlterarPetCommand {
    public String petId;
    public String donoId;
    @NotNull
    public String nome;
    @NotNull
    public int idade;

    public AlterarPetCommand(String petId, String donoId) {
        this.petId = petId;
        this.donoId = donoId;
    }
}
