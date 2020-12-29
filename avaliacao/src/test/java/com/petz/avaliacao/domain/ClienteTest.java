package com.petz.avaliacao.domain;

import com.petz.avaliacao.domain.clientes.Cliente;
import com.petz.avaliacao.domain.clientes.Pet;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Calendar;
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
}
