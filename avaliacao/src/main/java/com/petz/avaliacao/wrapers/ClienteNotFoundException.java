package com.petz.avaliacao.wrapers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteNotFoundException extends Exception{
    public ClienteNotFoundException(String id) {
        super(String.format("Nenhum Cliente com este id %s foi encontrado no sistema.", id));
    }
}
