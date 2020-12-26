package com.petz.avaliacao.application.queries.cliente.responses;

public class PetResponse {
    public String id;
    //public String tipo;
    public String nome;
    public int idade;
    public Dono dono;

    public static class Dono{
        public String id;
    }
}
