package com.petz.avaliacao.services;

import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.services.ClienteService;
import com.petz.avaliacao.domain.clientes.Cliente;
import com.petz.avaliacao.infrastructure.mappers.ClienteMapper;
import com.petz.avaliacao.infrastructure.repositories.ClienteRepository;
import com.petz.avaliacao.wrapers.ClienteAlreadyRegisteredException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteMapper clienteMapper = ClienteMapper.INSTANCE;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void criarClienteComSucesso_Test() throws ClienteAlreadyRegisteredException {

        var command = new CriarClienteCommand("cliente","teste@gmail.com");
        var cliente = clienteMapper.converter(command);

        doReturn(cliente).when(clienteRepository).save(any());
        when(clienteRepository.findFirstByEmail(any())).thenReturn(Optional.empty());

        try {
            clienteService.adicionarCliente(command);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }

    }

    @Test
    void quando_Ja_Existe_Cliente_Cadastrado_Com_O_Mesmo_Email_Uma_Exception_Deve_Ser_Lancada_Test() {

        var command = new CriarClienteCommand("cliente","teste@gmail.com");
        var cliente = clienteMapper.converter(command);

        when(clienteRepository.findFirstByEmail(any())).thenReturn(Optional.of(cliente));

        assertThrows(ClienteAlreadyRegisteredException.class, () -> clienteService.adicionarCliente(command));
    }
}
