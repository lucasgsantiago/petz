package com.petz.apiclientes.domain;

import com.petz.apiclientes.domain.clientes.Cliente;
import com.petz.apiclientes.domain.clientes.Pet;
import com.petz.apiclientes.wrappers.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        this.cliente = new Cliente().builder()
                .id(UUID.randomUUID().toString())
                .email("teste@gmail.com")
                .nome("cliente")
                .dataCriacao(Calendar.getInstance().getTime())
                .build();
    }

    @Test
    public void adicionar_Pet_Com_Sucesso(){
        var pet = new Pet(UUID.randomUUID().toString(),"pet teste",2);

        this.cliente.adicionarPet(pet);

        assertThat(this.cliente.getPets().size(), is(equalTo(1)));
        assertThat(this.cliente.getPets().contains(pet), is(true));
    }

    @Test
    public void adicionar_Pet_Com_Id_Nulo_Deve_Lancar_Uma_Exception(){
        var pet = new Pet(UUID.randomUUID().toString(),"pet teste",2);
        pet.setId(null);

        assertThrows(IllegalArgumentException.class, () -> this.cliente.adicionarPet(pet));
    }

    @Test
    public void adicionar_Pet_Com_Nome_Nulo_Deve_Lancar_Uma_Exception(){
        var pet = new Pet(UUID.randomUUID().toString(),"pet teste",2);
        pet.setNome(null);

        assertThrows(IllegalArgumentException.class, () -> this.cliente.adicionarPet(pet));
    }

    @Test
    public void adicionar_Pet_Com_Idade_Nulo_Deve_Lancar_Uma_Exception(){
        var pet = new Pet(UUID.randomUUID().toString(),"pet teste",2);
        pet.setIdade(null);

        assertThrows(IllegalArgumentException.class, () -> this.cliente.adicionarPet(pet));
    }

    @Test
    public void deletar_Pet_Com_Sucesso() throws ResourceNotFoundException {
        var pet = new Pet(UUID.randomUUID().toString(),"pet teste",2);
        var pets = new HashSet<Pet>();
        pets.add(pet);
        var cliente = new Cliente().builder()
                .id(UUID.randomUUID().toString())
                .email("teste@gmail.com")
                .nome("cliente")
                .dataCriacao(Calendar.getInstance().getTime())
                .pets(pets)
                .build();
        int qtdPetsAntes = cliente.getPets().size();

        cliente.deletarPet(pet.getId());

        assertThat(qtdPetsAntes, is(equalTo(1)));
        assertThat(cliente.getPets().size(), is(equalTo(0)));
        assertThat(cliente.getPets().contains(pet), is(false));
    }

    @Test
    public void deletar_Pet_Com_Lista_Vazia_Deve_Lancar_Uma_Exception() {
        var pet = new Pet(UUID.randomUUID().toString(),"pet teste",2);
        var cliente = new Cliente();

        assertThrows(ResourceNotFoundException.class, () -> cliente.deletarPet(pet.getId()));
    }
}
