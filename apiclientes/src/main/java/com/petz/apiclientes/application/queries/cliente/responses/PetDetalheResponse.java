package com.petz.apiclientes.application.queries.cliente.responses;

public class PetDetalheResponse {
    public String id;
    //public String tipo;
    public String nome;
    public int idade;
    public Dono dono;

    public static class Dono{
        public String id;
    }
}
