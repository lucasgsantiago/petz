package com.petz.avaliacao.infrastructure.mappers;

public interface Mapper {
    <T, C> T converter(C source);
}
