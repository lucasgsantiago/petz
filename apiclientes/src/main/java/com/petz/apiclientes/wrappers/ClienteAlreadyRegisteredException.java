package com.petz.apiclientes.wrappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteAlreadyRegisteredException extends Exception{
    public ClienteAlreadyRegisteredException(String clienteEmail) {
        super(String.format("Cliente com o email %s já está registrado no sistema.", clienteEmail));
    }
}
