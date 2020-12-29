package com.petz.avaliacao.controllers;

import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.services.ClienteService;
import com.petz.avaliacao.presentation.controllers.ClienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.UUID;

import static com.petz.avaliacao.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
