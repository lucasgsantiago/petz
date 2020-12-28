package com.petz.avaliacao.services;

import com.petz.avaliacao.application.commands.cliente.AlterarClienteCommand;
import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.services.ClienteService;
import com.petz.avaliacao.domain.clientes.Cliente;
import com.petz.avaliacao.infrastructure.mappers.ClienteMapper;
import com.petz.avaliacao.infrastructure.repositories.ClienteRepository;
import com.petz.avaliacao.wrapers.BusinessException;
import com.petz.avaliacao.wrapers.ClienteAlreadyRegisteredException;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteMapper clienteMapper = ClienteMapper.INSTANCE;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void criar_Cliente_Com_Sucesso() {

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
    void criar_Cliente_Quando_Ja_Existe_Outro_Cadastrado_Com_O_Mesmo_Email_Uma_Exception_Deve_Ser_Lancada() {

        var command = new CriarClienteCommand("cliente","teste@gmail.com");
        var cliente = clienteMapper.converter(command);

        when(clienteRepository.findFirstByEmail(any())).thenReturn(Optional.of(cliente));

        assertThrows(ClienteAlreadyRegisteredException.class, () -> clienteService.adicionarCliente(command));
    }

    @Test
    void deletar_Cliente_Com_Sucesso() {

        var cliente = new Cliente().builder()
                .id(UUID.randomUUID().toString())
                .email("teste@gmail.com")
                .nome("cliente")
                .dataCriacao(Calendar.getInstance().getTime())
                .build();

        doNothing().when(clienteRepository).delete(any());
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));

        try {
            clienteService.deletarCliente(cliente.getId());
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deletar_Cliente_Inexistente_Uma_Exception_Deve_Ser_Lancada() {

        when(clienteRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> clienteService.deletarCliente(UUID.randomUUID().toString()));
    }

    @Test
    void alterar_Cliente_Com_Sucesso() {
        var cliente = new Cliente().builder()
                .id(UUID.randomUUID().toString())
                .email("teste@gmail.com")
                .nome("cliente")
                .dataCriacao(Calendar.getInstance().getTime())
                .build();
        var command = new AlterarClienteCommand(cliente.getId(),"outro nome","teste@gmail.com");

        when(clienteRepository.findFirstByEmail(any())).thenReturn(Optional.of(cliente));
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        doReturn(cliente).when(clienteRepository).save(any());

        try {
            clienteService.alterarCliente(command);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void alterar_Cliente_Com_Email_Ja_Cadastrado_Em_Outro_Cliente_Uma_Exception_Deve_Ser_Lancada() {

        var cliente = new Cliente().builder()
                .id(UUID.randomUUID().toString())
                .email("teste@gmail.com")
                .nome("cliente")
                .build();

        var outroCliente = new Cliente().builder()
                .id(UUID.randomUUID().toString())
                .email("outroTeste@gmail.com")
                .nome("outro cliente")
                .build();

        var command = new AlterarClienteCommand(cliente.getId(),"outro nome",outroCliente.getEmail());

        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        when(clienteRepository.findFirstByEmail(any())).thenReturn(Optional.of(outroCliente));

        assertThrows(BusinessException.class, () -> clienteService.alterarCliente(command));
    }

}
