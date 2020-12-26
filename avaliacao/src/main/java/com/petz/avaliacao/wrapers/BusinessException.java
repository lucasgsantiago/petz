package com.petz.avaliacao.wrapers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessException extends Exception{

    private static final long serialVersionUID = 1L;

    public BusinessException(String message){
        super(message);
    }
}