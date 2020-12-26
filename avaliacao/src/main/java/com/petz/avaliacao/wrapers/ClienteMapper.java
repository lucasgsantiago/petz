package com.petz.avaliacao.wrapers;


import com.petz.avaliacao.application.commands.cliente.AdicionarPetCommand;
import com.petz.avaliacao.application.queries.cliente.responses.ClienteResponse;
import com.petz.avaliacao.application.queries.cliente.responses.PetResponse;
import com.petz.avaliacao.domain.Clientes.Cliente;
import com.petz.avaliacao.domain.Clientes.Pet;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper{
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Pet converter(AdicionarPetCommand source);
    ClienteResponse converter(Cliente source);
    PetResponse converter(Pet source);

    @IterableMapping(elementTargetType = ClienteResponse.class)
    List<ClienteResponse> converter(List<Cliente> clientes);

    @IterableMapping(elementTargetType = PetResponse.class)
    List<PetResponse> converterPets(List<Pet> pets);
}
