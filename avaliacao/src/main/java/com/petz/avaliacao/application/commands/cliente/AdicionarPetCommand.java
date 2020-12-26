package com.petz.avaliacao.application.commands.cliente;

import java.util.UUID;

public class AdicionarPetCommand {
    public String id = UUID.randomUUID().toString();
    public String donoId;
    public String nome;
    public int idade;
}
