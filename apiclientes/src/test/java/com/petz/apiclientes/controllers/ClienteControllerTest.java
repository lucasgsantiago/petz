package com.petz.apiclientes.controllers;

import com.petz.apiclientes.application.commands.cliente.AdicionarPetCommand;
import com.petz.apiclientes.application.commands.cliente.CriarClienteCommand;
import com.petz.apiclientes.application.services.ClienteService;
import com.petz.apiclientes.presentation.controllers.ClienteController;
import com.petz.apiclientes.wrappers.ClienteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Equals;
import org.mockito.internal.matchers.NotNull;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.UUID;

import static com.petz.apiclientes.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    private static final String RESOURCE_API_URL_PATH = "/api/v1/clientes";

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void criar_Cliente_Post_Com_Sucesso() throws Exception {
        // given
        var command = new CriarClienteCommand("cliente","teste@gmail.com");

        // when
        doNothing().when(clienteService).adicionarCliente(any());

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(command)))
                .andExpect(status().isCreated());
    }

    @Test
    void criar_Cliente_Com_Nome_Nulo_Um_Erro_Deve_Ser_Retornado() throws Exception {
        // given
        var command = new CriarClienteCommand();
        command.email = "teste@gmail.com";

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criar_Cliente_Com_Email_Nulo_Um_Erro_Deve_Ser_Retornado() throws Exception {
        // given
        var command = new CriarClienteCommand();
        command.nome = "teste";

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criar_Cliente_Com_Email_Invalido_Um_Erro_Deve_Ser_Retornado() throws Exception {
        // given
        var command = new CriarClienteCommand("teste","emailinvalido");

        // then
        mockMvc.perform(post(RESOURCE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletar_Cliente_Com_Id_Valido_Status_NO_CONTENT_Deve_Ser_Retornado() throws Exception {
        // given
        var clienteId = UUID.randomUUID().toString();

        //when
        doNothing().when(clienteService).deletarCliente(any());

        // then
        mockMvc.perform(delete(RESOURCE_API_URL_PATH + "/" + clienteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    void adicionar_Pet_Com_Sucesso() throws Exception {
        // given
        var clienteId = UUID.randomUUID().toString();
        var command = new AdicionarPetCommand(clienteId,"pet",1);

        // when
        doNothing().when(clienteService).adicionarPet(any());

        // then
        mockMvc.perform(
                post(new StringBuilder(RESOURCE_API_URL_PATH).append("/").append(clienteId).append("/pets").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(command)))
                .andExpect(status().isCreated());
    }


    @Test
    void adicionar_Pet_Com_Nome_Nulo_Um_Erro_Deve_Ser_Retornado() throws Exception {
        // given
        var clienteId = UUID.randomUUID().toString();
        var command = new AdicionarPetCommand(clienteId,null,1);

        // then
        mockMvc.perform(
                post(new StringBuilder(RESOURCE_API_URL_PATH).append("/").append(clienteId).append("/pets").toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void adicionar_Pet_Com_Idade_Nulo_Um_Erro_Deve_Ser_Retornado() throws Exception {
        // given
        var clienteId = UUID.randomUUID().toString();
        var command = new AdicionarPetCommand(clienteId,"pet",null);

        // then
        mockMvc.perform(
                post(new StringBuilder(RESOURCE_API_URL_PATH).append("/").append(clienteId).append("/pets").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void adicionar_Pet_Valido_Com_ClienteId_Invalido_Um_Erro_Deve_Ser_Retornado() throws Exception {
        // given
        var clienteId = UUID.randomUUID().toString();
        var command = new AdicionarPetCommand(clienteId,"pet",1);

        // when
        doThrow(ClienteNotFoundException.class).when(clienteService).adicionarPet(any());

        // then
        mockMvc.perform(
                post(new StringBuilder(RESOURCE_API_URL_PATH).append("/").append(clienteId).append("/pets").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(command)))
                .andExpect(status().isBadRequest());
    }
}
