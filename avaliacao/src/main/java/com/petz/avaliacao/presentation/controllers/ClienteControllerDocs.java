package com.petz.avaliacao.presentation.controllers;

import com.petz.avaliacao.application.commands.cliente.CriarClienteCommand;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.wrapers.ClienteAlreadyRegisteredException;
import com.petz.avaliacao.wrapers.ClienteNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api("Manages cliente stock")
public interface ClienteControllerDocs {@ApiOperation(value = "Cliente creation operation")

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success beer creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    String criarCliente(CriarClienteCommand command) throws ClienteAlreadyRegisteredException;

    @ApiOperation(value = "Returns beer found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success beer found in the system"),
            @ApiResponse(code = 404, message = "Beer with given name not found.")
    })
    ClienteResponse findByName(@PathVariable String name) throws ClienteNotFoundException;

    @ApiOperation(value = "Returns a list of all beers registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all beers registered in the system"),
    })
    List<ClienteResponse> listBeers();

    @ApiOperation(value = "Delete a beer found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success beer deleted in the system"),
            @ApiResponse(code = 404, message = "Beer with given id not found.")
    })
    void deleteById(@PathVariable String id) throws ClienteNotFoundException;
}
