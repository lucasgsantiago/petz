package com.petz.apiclientes.application.commands.cliente;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DeletarPetCommand {
    public String donoId;
    public String petId;
}
