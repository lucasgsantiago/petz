package com.petz.avaliacao.domain.clientes;

import com.petz.avaliacao.wrapers.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Cliente implements Serializable {

    @Id
    private String id;
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    private Date dataCriacao;
    private Date dataUltimaAlteracao;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "dono", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Pet> pets;

    public Cliente() {
        this.pets = new HashSet<>();
        this.dataCriacao = new Date();
    }

    public Cliente(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCriacao = new Date();
        this.pets = new HashSet<>();
    }

    public void adicionarPet(Pet pet){
        if(pets == null){
            pets = new HashSet<>();
        }
        pet.setDataCriacao(new Date());
        pet.setDono(this);
        pets.add(pet);
    }

    public void alterarPet(Pet newPet) throws ResourceNotFoundException {
        if(pets.isEmpty()) throw new ResourceNotFoundException("Este Cliente não possui nenhum Pet cadastrado");
        var pet = pets.stream().filter(p -> p.getId().equalsIgnoreCase(newPet.getId())).findFirst();
        if(pet.isPresent()){
            pet.get().atualizar(newPet.getNome(),newPet.getIdade());
        }else{
            throw new ResourceNotFoundException("Nenhum Pet foi encontrado com este Id: "+newPet.getId());
        }
    }

    public void alterar(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.dataUltimaAlteracao = new Date();
    }

    public void deletarPet(String petId) throws ResourceNotFoundException {
        if(pets.isEmpty()) throw new ResourceNotFoundException("Nenhum Pet foi encontrado com este Id: "+petId);
        var optPet = pets.stream().filter(pet -> pet.getId().equalsIgnoreCase(petId)).findFirst();
        if(optPet.isPresent()){
            pets.remove(optPet.get());
        }

    }

}
