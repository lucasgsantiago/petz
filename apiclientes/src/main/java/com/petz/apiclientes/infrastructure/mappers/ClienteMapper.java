package com.petz.apiclientes.infrastructure.mappers;


import com.petz.apiclientes.application.commands.cliente.AdicionarPetCommand;
import com.petz.apiclientes.application.commands.cliente.AlterarPetCommand;
import com.petz.apiclientes.application.commands.cliente.CriarClienteCommand;
import com.petz.apiclientes.application.queries.cliente.responses.*;
import com.petz.apiclientes.domain.clientes.Cliente;
import com.petz.apiclientes.domain.clientes.Pet;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper{
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente converter(CriarClienteCommand command);
    Pet converter(AdicionarPetCommand source);
    @Mapping(source = "petId", target = "id")
    Pet converter(AlterarPetCommand source);
    ClienteResponse converter(Cliente source);
    ClienteDetalheResponse converterDetalhe(Cliente source);
    PetDetalheResponse converter(Pet source);
    PetResponse converterPet(Pet source);

    @IterableMapping(elementTargetType = ClienteResponse.class)
    List<ClienteResponse> converter(List<Cliente> clientes);

    @IterableMapping(elementTargetType = PetResponse.class)
    List<PetResponse> converterPets(List<Pet> pets);

}
