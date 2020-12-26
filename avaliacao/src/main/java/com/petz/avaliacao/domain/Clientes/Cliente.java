package com.petz.avaliacao.domain.Clientes;

import com.petz.avaliacao.wrapers.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class Cliente implements Serializable {

    @Id
    private String id;
    private String nome;
    private String email;
    @OneToMany(cascade = {CascadeType.REMOVE,CascadeType.MERGE}, mappedBy = "dono", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Pet> pets;

    public Cliente() {
        this.pets = new HashSet<>();
    }

    public Cliente(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.pets = new HashSet<>();
    }

    public void adicionarPet(Pet pet){
        if(pets == null){
            pets = new HashSet<>();
        }
        pet.setDono(this);
        pets.add(pet);
    }

    public void alterar(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public void deletarPet(String petId) throws ResourceNotFoundException {
        if(pets.isEmpty()) throw new ResourceNotFoundException("Nenhum Pet foi encontrado com este Id: "+petId);
        //var optPet = pets.stream().filter(pet -> pet.getId().equalsIgnoreCase(petId)).findAny();
        pets.stream().filter(pet -> pet.getId().equalsIgnoreCase(petId)).findAny().ifPresentOrElse(
                pet ->
                        pets.remove(pet),
                        () -> new ResourceNotFoundException("Nenhum Pet foi encontrado com este Id: "+petId)
        );

    }
}
