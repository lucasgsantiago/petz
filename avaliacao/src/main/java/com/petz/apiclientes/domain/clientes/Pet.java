package com.petz.apiclientes.domain.clientes;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet implements Serializable {

    @Id
    private String id;
    @NotNull
    private String nome;
    @NotNull
    private Integer idade;
    @NotNull
    private Date dataCriacao;
    private Date dataUltimaAlteracao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente dono;

    public Pet(String id) {
        this.id = id;
    }

    public Pet(String id,String nome, Integer idade){
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.dataCriacao = new Date();
    }

    public void atualizar(String nome, Integer idade){
        this.nome = nome;
        this.idade = idade;
        this.dataUltimaAlteracao = new Date();
    }

    public void setDono(Cliente dono){
        this.dono = dono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id.equals(pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
