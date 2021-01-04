package com.petz.apiclientes.application.commands.cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;
@NoArgsConstructor
public class AdicionarPetCommand {
    @JsonIgnore
    public String id = UUID.randomUUID().toString();
    public String donoId;
    @NotNull
    @Size(max = 20)
    public String nome;
    @NotNull
    @Min(0)
    public Integer idade;

    public AdicionarPetCommand(String donoId, @NotNull String nome, @NotNull Integer idade) {
        this.donoId = donoId;
        this.nome = nome;
        this.idade = idade;
    }
}
