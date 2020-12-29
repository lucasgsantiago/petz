package com.petz.apiclientes.domain.clientes;

import com.petz.apiclientes.wrappers.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.util.StringHelper;

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
        validarPet(pet);
        if(pets == null){
            pets = new HashSet<>();
        }
        pet.setDataCriacao(new Date());
        pet.setDono(this);
        pets.add(pet);
    }

    public void alterarPet(Pet newPet) throws ResourceNotFoundException {
        if(pets.isEmpty()) throw new ResourceNotFoundException("Nenhum Pet foi encontrado para esse Cliente");
        var pet = obterPet(newPet.getId());
        pet.atualizar(newPet.getNome(),newPet.getIdade());
    }

    public void alterar(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.dataUltimaAlteracao = new Date();
    }

    public void deletarPet(String petId) throws ResourceNotFoundException {
        if(pets.isEmpty()) throw new ResourceNotFoundException("Nenhum Pet foi encontrado para esse Cliente");
        var pet = obterPet(petId);
        pets.remove(pet);
    }

    public Pet obterPet(String petId) throws ResourceNotFoundException {
        var optPet = pets.stream().filter(pet -> pet.getId().equalsIgnoreCase(petId)).findFirst();
        if(!optPet.isPresent()){
            throw new ResourceNotFoundException("Nenhum Pet foi encontrado com este Id: "+petId);
        }
        return optPet.get();
    }

    private void validarPet(Pet pet){
        if(StringHelper.isEmpty(pet.getId()) || StringHelper.isEmpty(pet.getNome()) || pet.getIdade() == null){
            throw new IllegalArgumentException("Campos obrigatórios para criação de um Pet não foram informados ou são inválidos");
        }
    }

}
