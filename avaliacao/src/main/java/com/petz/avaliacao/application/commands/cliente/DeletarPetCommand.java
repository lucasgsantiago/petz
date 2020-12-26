package com.petz.avaliacao.application.commands.cliente;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DeletarPetCommand {
    public String donoId;
    public String petId;
}
