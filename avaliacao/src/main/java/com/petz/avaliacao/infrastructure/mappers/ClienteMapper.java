package com.petz.avaliacao.infrastructure.mappers;


import com.petz.avaliacao.application.commands.cliente.AdicionarPetCommand;
import com.petz.avaliacao.application.queries.cliente.responses.*;
import com.petz.avaliacao.domain.clientes.Cliente;
import com.petz.avaliacao.domain.clientes.Pet;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper{
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Pet converter(AdicionarPetCommand source);
    ClienteResponse converter(Cliente source);
    ClienteComPetsResponse converterComPets(Cliente source);
    PetComDonoResponse converter(Pet source);
    PetResponse converterPet(Pet source);

    @IterableMapping(elementTargetType = ClienteResponse.class)
    List<ClienteResponse> converter(List<Cliente> clientes);

    @IterableMapping(elementTargetType = PetResponse.class)
    List<PetResponse> converterPets(List<Pet> pets);

}
