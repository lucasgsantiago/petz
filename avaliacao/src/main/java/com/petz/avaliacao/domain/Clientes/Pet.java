package com.petz.avaliacao.domain.Clientes;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet implements Serializable {

    @Id
    private String id;
   // private String tipo;
    private String nome;
    private int idade;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente dono;

    public Pet(String id) {
        this.id = id;
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
